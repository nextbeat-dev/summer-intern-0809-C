delete from "employer";
INSERT INTO "employer"("location_id", "name", "address", "email", "phone") VALUES ('01000','hiroto','北海道','hiroto@gmail.com','000-0000-0000');

delete from "employer_post";
INSERT INTO "employer_post"("id","employer_id", "location_id", "title", "address", "price", "job_date", "description", "category_id_1", "category_id_2", "category_id_3", "main_image", "thumbnail_image") VALUES ('1', '1','01000','ミカンもぎたい人wanted','北海道', 10000,'2019-09-01', '楽しく稼げる!', 2, 3, 4, 'google.com', 'google.com');