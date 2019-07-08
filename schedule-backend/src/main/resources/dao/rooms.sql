SELECT distinct a1 AS ID, a2 AS NAME from A
inner join KAA on (A.a1 = KAA.kaa2)
inner join KA on (KAA.kaa1 = KA.ka1)
WHERE 1=1