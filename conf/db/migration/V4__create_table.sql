CREATE TABLE IF NOT EXISTS "applicant" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "location_id" VARCHAR(8)   NOT NULL,
  "name"        VARCHAR(255) NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "email"       VARCHAR(255) DEFAULT NULL,
  "phone"       VARCHAR(255) DEFAULT NULL,
  "description" TEXT         DEFAULT NULL,
  "image"       VARCHAR(255) DEFAULT NULL,
  "score"       INT          DEFAULT 3,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS "employer" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "location_id" VARCHAR(8)   NOT NULL,
  "name"        VARCHAR(255) NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "email"       VARCHAR(255) DEFAULT NULL,
  "phone"       VARCHAR(255) DEFAULT NULL,
  "description" TEXT         DEFAULT NULL,
  "image"       VARCHAR(255) DEFAULT NULL,
  "score"       INT          DEFAULT 3,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS "applicant_post" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "applicant_id"  INT          NOT NULL,
  "location_id" VARCHAR(8)   NOT NULL,
  "title"        VARCHAR(255) NOT NULL,
  "destination"     VARCHAR(255) NOT NULL,
  "description" TEXT         DEFAULT NULL,
  "category_id_1" INT        DEFAULT NULL,
  "category_id_2" INT        DEFAULT NULL,
  "category_id_3" INT        DEFAULT NULL,
  "done"        BOOLEAN      DEFAULT false,
  "free_date"    DATE   NOT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS "employer_post" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "employer_id"  INT          NOT NULL,
  "location_id" VARCHAR(8)   NOT NULL,
  "title"        VARCHAR(255) NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "description" TEXT         DEFAULT NULL,
  "main_image"   VARCHAR(255) DEFAULT NULL,
  "thumbnail_image" VARCHAR(255) DEFAULT NULL,
  "price"       INT          NOT NULL,
  "category_id_1" INT        DEFAULT NULL,
  "category_id_2" INT        DEFAULT NULL,
  "category_id_3" INT        DEFAULT NULL,
  "done"        BOOLEAN      DEFAULT false,
  "job_date"    DATE   NOT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS "category" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "name"        VARCHAR(255) NOT NULL,  
  "description" TEXT         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- applicantがしたフィードバック
CREATE TABLE IF NOT EXISTS "applicant_feedback_log" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "employer_id" INT          NOT NULL,
  "applicant_id"INT          NOT NULL,
  "score"       Int         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- employerがしたフィードバック
CREATE TABLE IF NOT EXISTS "employer_feedback_log" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "employer_id" INT          NOT NULL,
  "applicant_id"INT          NOT NULL,
  "score"       Int         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;