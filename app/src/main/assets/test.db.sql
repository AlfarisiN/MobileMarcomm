BEGIN TRANSACTION;
DROP TABLE IF EXISTS `question_header`;
CREATE TABLE IF NOT EXISTS `question_header` (
	`q_id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`q_code`	TEXT,
	`q_name`	TEXT,
	`q_type`	TEXT
);
INSERT INTO `question_header` (q_id,q_code,q_name,q_type) VALUES (1,'Q001','Bootcamp',NULL),
 (2,'Q002','eLearning',NULL),
 (3,'Q003','Psikotest',NULL);
DROP TABLE IF EXISTS `Question_Detail`;
CREATE TABLE IF NOT EXISTS `Question_Detail` (
	`q_id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`q_qh_id`	INTEGER,
	`q_no`	TEXT,
	`q_question`	TEXT
);
INSERT INTO `Question_Detail` (q_id,q_qh_id,q_no,q_question) VALUES (1,1,'1','apakah...'),
 (2,2,'2','apakah...'),
 (3,3,'3','bagaimana');
COMMIT;
