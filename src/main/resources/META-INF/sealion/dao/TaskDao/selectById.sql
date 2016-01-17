SELECT t.id, t.title, t.content, t.status, t.postedBy, t.postedAt, t.milestone,
       a.username as accountName, m.name as milestoneName
FROM Task t
LEFT OUTER JOIN Milestone m
ON m.id = t.milestone
INNER JOIN Account a
ON a.id = t.postedBy
WHERE t.id = /* id */1