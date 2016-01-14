/*
rootアカウントを作成する
初期パスワードはroot
USER・ADMINの両権限を持つ
*/
INSERT INTO Account (username) VALUES ('root');
INSERT INTO Password (account, hash, salt, hashAlgorithm) SELECT id, 'root', 'none', 'plain' FROM Account WHERE username = 'root';
INSERT INTO Grant (account, role) SELECT id, 'USER' FROM Account WHERE username = 'root';
INSERT INTO Grant (account, role) SELECT id, 'ADMIN' FROM Account WHERE username = 'root';
