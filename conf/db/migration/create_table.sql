CREATE TABLE "company" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "location_id" VARCHAR(8)   NOT NULL,
  "name"        VARCHAR(255) NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "description" TEXT         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "service" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "company_id"  INT          NOT NULL,
  "location_id" VARCHAR(8)   NOT NULL,
  "name"        VARCHAR(255) NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "description" TEXT         DEFAULT NULL,
  "main_image"   VARCHAR(255) DEFAULT NULL,
  "thumbnail_image" VARCHAR(255) DEFAULT NULL,
  "price"       INT          NOT NULL,
  "category_id_1" INT        DEFAULT NULL,
  "category_id_2" INT        DEFAULT NULL,
  "category_id_3" INT        DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "category" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "service_id" VARCHAR(8)   NOT NULL,
  "name"        VARCHAR(255) NOT NULL,  
  "description" TEXT         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;