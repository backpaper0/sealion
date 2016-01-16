SELECT t.id, t.title, t.status, t.milestone, m.name as milestoneName
FROM Task t
LEFT OUTER JOIN Milestone m
ON t.milestone = m.id
WHERE t.project = /* project */1