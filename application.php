-- -----------------------------------------------------
-- Table `groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `groups` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(180) NOT NULL,
  `group_icon` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `groups_name_unique` (`name` ASC))
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `full_name` VARCHAR(255) NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email` VARCHAR(245) NOT NULL,
  `avatar` VARCHAR(100) NOT NULL,
  `moderator` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_unique` (`username` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `post` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` VARCHAR(855) NOT NULL,
  `likes` INT(11) NULL DEFAULT '0',
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` INT(11) NULL DEFAULT NULL,
  `group_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_post_user` (`user_id` ASC) ,
  INDEX `fk_post_group_id` (`group_id` ASC) ,
  CONSTRAINT `fk_post_group_id`
    FOREIGN KEY (`group_id`)
    REFERENCES `groups` (`id`),
  CONSTRAINT `fk_post_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `commentary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `commentary` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(850) NOT NULL,
  `made` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `likes` INT(11) NOT NULL DEFAULT '0',
  `post_id` INT(11) NULL DEFAULT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `post_id` (`post_id` ASC),
  INDEX `user_id` (`user_id` ASC) ,
  CONSTRAINT `commentary_foreign_key1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`),
  CONSTRAINT `commentary_foreign_key2`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `groups_has_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `groups_has_users` (
  `group_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`group_id`, `user_id`),
  INDEX `fk_groups_has_users1_idx` (`user_id` ASC) ,
  INDEX `fk_groups_has_users2_idx` (`group_id` ASC),
  CONSTRAINT `groups_has_users_foreign_key1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`),
  CONSTRAINT `groups_has_users_foreign_key2`
    FOREIGN KEY (`group_id`)
    REFERENCES `groups` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `likes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `post_id` INT(11) NOT NULL,
  `commentary_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `likes_foreign_key1` (`user_id` ASC) ,
  INDEX `likes_foreign_key2` (`post_id` ASC) ,
  INDEX `commentary_foreign_key` (`commentary_id` ASC) ,
  CONSTRAINT `commentary_foreign_key`
    FOREIGN KEY (`commentary_id`)
    REFERENCES `commentary` (`id`),
  CONSTRAINT `post_foreign_key`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`),
  CONSTRAINT `user_foreign_key`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `user_visited`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_visited` (
  `user_id` INT(11) NOT NULL,
  `group_id` INT(11) NOT NULL,
  `visited` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `fk_user_visited_gid` (`group_id` ASC) ,
  INDEX `fk_user_visited_uid` (`user_id` ASC) ,
  CONSTRAINT `fk_user_visited_gid`
    FOREIGN KEY (`group_id`)
    REFERENCES `groups` (`id`),
  CONSTRAINT `fk_user_visited_uid`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


