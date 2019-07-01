select distinct P1 as ID, P3 as LAST_NAME, P4 as SUR_NAME, P5 as FIRST_NAME, P3 || ' ' || P4 || ' ' || P5 as FULL_NAME
                from dol
                inner join pd on (dol.dol1 = pd.pd45)
                inner join p on (pd.pd2 = p.p1)
where p1 = ?