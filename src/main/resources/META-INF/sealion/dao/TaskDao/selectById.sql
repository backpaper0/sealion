SELECT t.id, t.title, t.content, t.status, t.postedBy, t.postedAt, b.milestone,
       a.username as accountName, m.name as milestoneName
FROM Task t
LEFT OUTER JOIN Bundle b
ON b.task = t.id
LEFT OUTER JOIN Milestone m
ON m.id = b.milestone
INNER JOIN Account a
ON a.id = t.postedBy
WHERE t.id = /* id */1