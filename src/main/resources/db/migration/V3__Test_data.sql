-- 動作確認用のデータ
-- このSQLファイルは実装がある程度落ち着いたら消す
INSERT INTO Account (username, email) VALUES ('foo', 'foo@localhost');
INSERT INTO Account (username, email) VALUES ('bar', 'bar@localhost');
INSERT INTO Project (name) VALUES ('ほげプロジェクト');
INSERT INTO Project (name) VALUES ('ふがプロジェクト');

SET @foo = SELECT id FROM Account WHERE username = 'foo';
SET @bar = SELECT id FROM Account WHERE username = 'bar';
SET @hoge = SELECT id FROM Project WHERE name = 'ほげプロジェクト';

INSERT INTO Milestone (name, fixedDate, project) VALUES ('v32.0-birthday', '2016-02-10', @hoge);

SET @m = SELECT id FROM Project WHERE name = 'ほげプロジェクト';

INSERT INTO Task (title, content, status, postedBy, postedAt, project) VALUES ('あれやる', 'あれをやる。がんばる。', 'OPEN', @foo, NOW(), @hoge);
INSERT INTO Task (title, content, status, postedBy, postedAt, project) VALUES ('これやる', 'これをやる。がんばらない程度に。', 'DOING', @bar, NOW(), @hoge);
-- マイルストーン設定しないタスク
INSERT INTO Task (title, content, status, postedBy, postedAt, project) VALUES ('それやる', NULL, 'DONE', @foo, NOW(), @hoge);

SET @are = SELECT id FROM Task WHERE title = 'あれやる';
SET @kore = SELECT id FROM Task WHERE title = 'これやる';

INSERT INTO Bundle (task, milestone) VALUES (@are, @m);
INSERT INTO Bundle (task, milestone) VALUES (@kore, @m);

INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@are, 'コメントです。', @foo, NOW());
INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@kore, 'コメントだす。', @bar, NOW());
INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@are, 'コメントにょす。', @bar, NOW());
INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@are, 'コメントもふもふ。', @foo, NOW());
