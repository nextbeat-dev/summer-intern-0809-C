CREATE TABLE "applicant" (
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

CREATE TABLE "employer" (
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

CREATE TABLE "applicant_post" (
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

CREATE TABLE "employer_post" (
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

CREATE TABLE "category" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "name"        VARCHAR(255) NOT NULL,  
  "description" TEXT         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- applicantがしたフィードバック
CREATE TABLE "applicant_feedback_log" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "employer_id" INT          NOT NULL,
  "applicant_id"INT          NOT NULL,
  "score"       Int         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- employerがしたフィードバック
CREATE TABLE "employer_feedback_log" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "employer_id" INT          NOT NULL,
  "applicant_id"INT          NOT NULL,
  "score"       Int         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS "geo_location" (
  "id"         varchar(8)  NOT     NULL PRIMARY KEY,
  "level"      TINYINT     NOT     NULL,
  "typedef"    TINYINT     NOT     NULL,
  "parent"     varchar(8)  DEFAULT NULL,
  "urn"        varchar(64) DEFAULT NULL,
  "region"     varchar(32) NOT     NULL,
  "region_en"  varchar(32) DEFAULT NULL,
  "pref"       varchar(32) NOT     NULL,
  "pref_en"    varchar(32) DEFAULT NULL,
  "city"       varchar(32) DEFAULT NULL,
  "city_en"    varchar(32) DEFAULT NULL,
  "ward"       varchar(32) DEFAULT NULL,
  "ward_en"    varchar(32) DEFAULT NULL,
  "county"     varchar(32) DEFAULT NULL,
  "county_en"  varchar(32) DEFAULT NULL,
  "updated_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY "geo_location_ukey01" ("urn"),
         KEY "geo_location_key01"  ("parent")
) ENGINE=InnoDB;