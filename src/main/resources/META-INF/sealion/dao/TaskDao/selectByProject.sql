SELECT t.id, t.title, t.status, b.milestone, m.name as milestoneName
FROM Task t
LEFT OUTER JOIN Bundle b
ON b.task = t.id
LEFT OUTER JOIN Milestone m
ON m.id = b.milestone
WHERE t.project = /* project */1