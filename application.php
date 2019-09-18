-- -----------------------------------------------------
-- Table `application`.`groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `application`.`groups` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(180) NOT NULL,
  `group_icon` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `groups_name_unique` (`name` ASC))
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `application`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `application`.`user` (
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
-- Table `application`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `application`.`post` (
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
    REFERENCES `application`.`groups` (`id`),
  CONSTRAINT `fk_post_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `application`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `application`.`commentary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `application`.`commentary` (
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
    REFERENCES `application`.`post` (`id`),
  CONSTRAINT `commentary_foreign_key2`
    FOREIGN KEY (`user_id`)
    REFERENCES `application`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `application`.`groups_has_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `application`.`groups_has_users` (
  `group_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`group_id`, `user_id`),
  INDEX `fk_groups_has_users1_idx` (`user_id` ASC) ,
  INDEX `fk_groups_has_users2_idx` (`group_id` ASC),
  CONSTRAINT `groups_has_users_foreign_key1`
    FOREIGN KEY (`user_id`)
    REFERENCES `application`.`user` (`id`),
  CONSTRAINT `groups_has_users_foreign_key2`
    FOREIGN KEY (`group_id`)
    REFERENCES `application`.`groups` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `application`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `application`.`likes` (
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
    REFERENCES `application`.`commentary` (`id`),
  CONSTRAINT `post_foreign_key`
    FOREIGN KEY (`post_id`)
    REFERENCES `application`.`post` (`id`),
  CONSTRAINT `user_foreign_key`
    FOREIGN KEY (`user_id`)
    REFERENCES `application`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `application`.`user_visited`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `application`.`user_visited` (
  `user_id` INT(11) NOT NULL,
  `group_id` INT(11) NOT NULL,
  `visited` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `fk_user_visited_gid` (`group_id` ASC) ,
  INDEX `fk_user_visited_uid` (`user_id` ASC) ,
  CONSTRAINT `fk_user_visited_gid`
    FOREIGN KEY (`group_id`)
    REFERENCES `application`.`groups` (`id`),
  CONSTRAINT `fk_user_visited_uid`
    FOREIGN KEY (`user_id`)
    REFERENCES `application`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `application` ;

-- -----------------------------------------------------
-- procedure deletePost
-- -----------------------------------------------------

DELIMITER $$
USE `application`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deletePost`(
    in p_id int (11)
)
begin
    DELETE FROM likes WHERE post_id = p_id;
    DELETE FROM commentary WHERE post_id = p_id;
    DELETE FROM post WHERE id = p_id;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure newPosts
-- -----------------------------------------------------

DELIMITER $$
USE `application`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `newPosts`(
    IN g_id INT (11),
    IN u_id INT (11)
)
BEGIN
    SELECT count(DISTINCT(post.id)) AS Antall
    FROM post, user_visited
    WHERE post.created > user_visited.visited 
    AND post.group_id = g_id 
    AND post.user_id != u_id 
    AND user_visited.group_id = g_id
    AND user_visited.user_id = u_id;
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
