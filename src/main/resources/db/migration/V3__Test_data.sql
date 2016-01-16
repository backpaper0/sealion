-- 動作確認用のデータ
-- このSQLファイルは実装がある程度落ち着いたら消す
INSERT INTO Account (username, email) VALUES ('foo', 'foo@localhost');
INSERT INTO Account (username, email) VALUES ('bar', 'bar@localhost');
INSERT INTO Project (name) VALUES ('ほげプロジェクト');
INSERT INTO Project (name) VALUES ('ふがプロジェクト');

SET @foo = SELECT id FROM Account WHERE username = 'foo';
SET @bar = SELECT id FROM Account WHERE username = 'bar';
SET @hoge = SELECT id FROM Project WHERE name = 'ほげプロジェクト';

INSERT INTO Milestone (name, fixedDate) VALUES ('v32.0-birthday', '2016-02-10');

SET @m = SELECT id FROM Project WHERE name = 'ほげプロジェクト';

INSERT INTO Task (title, content, status, postedBy, postedAt, project, milestone) VALUES ('あれやる', 'あれをやる。がんばる。', 'OPEN', @foo, CURRENT_DATE(), @hoge, @m);
INSERT INTO Task (title, content, status, postedBy, postedAt, project, milestone) VALUES ('これやる', 'これをやる。がんばらない程度に。', 'DOING', @bar, CURRENT_DATE(), @hoge, @m);
