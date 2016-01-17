SELECT c.content, c.postedBy, c.postedAt, a.username as accountName
FROM Comment c
INNER JOIN Account a
ON c.postedBy = a.id
WHERE c.task = /* task */1
