# Indexing Observations

## Single Column Index

Query:
WHERE from_account_id = 1

Without index:
Sequential scan, slower.

With index:
Index scan, faster.

---

## Compound Index

Index:
(status, created_at)

### Full Usage

WHERE status='PENDING'
AND created_at > recent date

Best performance.

### Partial Leftmost

WHERE status='PENDING'

Still useful.

### Partial Non-leftmost

WHERE created_at > date

Less effective because first indexed column missing.

---

## Conclusion

Indexes improve search speed significantly.
Compound indexes work best when query matches leftmost columns.