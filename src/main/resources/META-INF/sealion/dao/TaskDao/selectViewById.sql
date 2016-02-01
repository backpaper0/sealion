SELECT t.id, t.title, t.content, t.status, t.postedBy, t.postedAt, b.milestone,
       p.username as accountName, m.name as milestoneName,
       a.id as assignee, a.username as assigneeName      
FROM Task t
LEFT OUTER JOIN Bundle b
ON b.task = t.id
LEFT OUTER JOIN Milestone m
ON m.id = b.milestone
INNER JOIN Account p
ON p.id = t.postedBy
LEFT OUTER JOIN Assignment at
ON at.task = t.id
LEFT OUTER JOIN Account a
ON a.id = at.account
WHERE t.id = /* id */1