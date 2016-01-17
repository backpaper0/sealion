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

INSERT INTO Task (title, content, status, postedBy, postedAt, project, milestone) VALUES ('あれやる', 'あれをやる。がんばる。', 'OPEN', @foo, NOW(), @hoge, @m);
INSERT INTO Task (title, content, status, postedBy, postedAt, project, milestone) VALUES ('これやる', 'これをやる。がんばらない程度に。', 'DOING', @bar, NOW(), @hoge, @m);
-- マイルストーン設定していないタスク
INSERT INTO Task (title, content, status, postedBy, postedAt, project, milestone) VALUES ('それやる', NULL, 'DONE', @foo, NOW(), @hoge, NULL);

SET @are = SELECT id FROM Task WHERE title = 'あれやる';
SET @kore = SELECT id FROM Task WHERE title = 'これやる';

INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@are, 'コメントです。', @foo, NOW());
INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@kore, 'コメントだす。', @bar, NOW());
INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@are, 'コメントにょす。', @bar, NOW());
INSERT INTO Comment (task, content, postedBy, postedAt) VALUES (@are, 'コメントもふもふ。', @foo, NOW());
