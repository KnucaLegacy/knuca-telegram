SELECT ID, COURSE, NAME, SNAME from
(SELECT gr1 as id, sem4 as course, gr3 as name, (CASE
    WHEN sem4 = 1 THEN
    gr19
    WHEN sem4 = 2 THEN
    gr20
    WHEN sem4 = 3 THEN
    gr21
    WHEN sem4 = 4 THEN
    gr22
    WHEN sem4 = 5 THEN
    gr23
    WHEN sem4 = 6 THEN
    gr24
    WHEN sem4 = 7 THEN
    gr25
    ELSE gr3 END) sname
			from sp
			   inner join sg on (sp.sp1 = sg.sg2)
			   inner join sem on (sg.sg1 = sem.sem2)
			   inner join gr on (sg.sg1 = gr.gr2)
			   inner join ucgn on (gr.gr1 = ucgn.ucgn2)
			   inner join ucgns on (ucgn.ucgn1 = ucgns.ucgns2)
			   inner join ucxg on (ucgn.ucgn1 = ucxg.ucxg2)
			WHERE ucxg1<30000 and gr13=0 and gr6 is null -- no idea wtf
				 and sem3=:Y and ucgns5=:Y      --year
				 and sem5=:S and ucgns6=:S       --semester
				 and sp5 >= 1 and sp5 <=8      --faculty
				 and gr1=:ID
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5, sem4)