SELECT ID, COURSE, NAME, SNAME from
(SELECT gr1 as id, sem4 as course, gr3 as name, gr19 as sname
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
				 and sem4=1                     --course
				 and sp5 >= 2 and sp5 <=8      --faculty
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5
UNION All
SELECT gr1 as id, sem4 as course, gr3 as name, gr20 as sname
			from sp
			   inner join sg on (sp.sp1 = sg.sg2)
			   inner join sem on (sg.sg1 = sem.sem2)
			   inner join gr on (sg.sg1 = gr.gr2)
			   inner join ucgn on (gr.gr1 = ucgn.ucgn2)
			   inner join ucgns on (ucgn.ucgn1 = ucgns.ucgns2)
			   inner join ucxg on (ucgn.ucgn1 = ucxg.ucxg2)
			WHERE ucxg1<30000 and gr13=0 and gr6 is null
				 and sem3=:Y and ucgns5=:Y --year
				 and sem5=:S and ucgns6=:S       --semester
				 and sem4=2                    --course
				 and sp5 >= 2 and sp5 <=8      --faculty
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5
UNION ALL
SELECT gr1 as id, sem4 as course, gr3 as name, gr21 as sname
			from sp
			   inner join sg on (sp.sp1 = sg.sg2)
			   inner join sem on (sg.sg1 = sem.sem2)
			   inner join gr on (sg.sg1 = gr.gr2)
			   inner join ucgn on (gr.gr1 = ucgn.ucgn2)
			   inner join ucgns on (ucgn.ucgn1 = ucgns.ucgns2)
			   inner join ucxg on (ucgn.ucgn1 = ucxg.ucxg2)
			WHERE ucxg1<30000 and gr13=0 and gr6 is null
				 and sem3=:Y and ucgns5=:Y --year
				 and sem5=:S and ucgns6=:S       --semester
				 and sem4=3                    --course
				 and sp5 >= 2 and sp5 <=8      --faculty
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5
UNION ALL
SELECT gr1 as id, sem4 as course, gr3 as name, gr22 as sname
			from sp
			   inner join sg on (sp.sp1 = sg.sg2)
			   inner join sem on (sg.sg1 = sem.sem2)
			   inner join gr on (sg.sg1 = gr.gr2)
			   inner join ucgn on (gr.gr1 = ucgn.ucgn2)
			   inner join ucgns on (ucgn.ucgn1 = ucgns.ucgns2)
			   inner join ucxg on (ucgn.ucgn1 = ucxg.ucxg2)
			WHERE ucxg1<30000 and gr13=0 and gr6 is null
				 and sem3=:Y and ucgns5=:Y --year
				 and sem5=:S and ucgns6=:S       --semester
				 and sem4=4                    --course
				 and sp5 >= 2 and sp5 <=8      --faculty
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5
UNION ALL
SELECT gr1 as id, sem4 as course, gr3 as name, gr23 as sname
			from sp
			   inner join sg on (sp.sp1 = sg.sg2)
			   inner join sem on (sg.sg1 = sem.sem2)
			   inner join gr on (sg.sg1 = gr.gr2)
			   inner join ucgn on (gr.gr1 = ucgn.ucgn2)
			   inner join ucgns on (ucgn.ucgn1 = ucgns.ucgns2)
			   inner join ucxg on (ucgn.ucgn1 = ucxg.ucxg2)
			WHERE ucxg1<30000 and gr13=0 and gr6 is null
				 and sem3=:Y and ucgns5=:Y --year
				 and sem5=:S and ucgns6=:S       --semester
				 and sem4=5                    --course
				 and sp5 >= 2 and sp5 <=8      --faculty
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5
UNION ALL
SELECT gr1 as id, sem4 as course, gr3 as name, gr24 as sname
			from sp
			   inner join sg on (sp.sp1 = sg.sg2)
			   inner join sem on (sg.sg1 = sem.sem2)
			   inner join gr on (sg.sg1 = gr.gr2)
			   inner join ucgn on (gr.gr1 = ucgn.ucgn2)
			   inner join ucgns on (ucgn.ucgn1 = ucgns.ucgns2)
			   inner join ucxg on (ucgn.ucgn1 = ucxg.ucxg2)
			WHERE ucxg1<30000 and gr13=0 and gr6 is null
				 and sem3=:Y and ucgns5=:Y --year
				 and sem5=:S and ucgns6=:S       --semester
				 and sem4=6                    --course
				 and sp5 >= 2 and sp5 <=8      --faculty
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5
UNION ALL
SELECT gr1 as id, sem4 as course, gr3 as name, gr25 as sname
			from sp
			   inner join sg on (sp.sp1 = sg.sg2)
			   inner join sem on (sg.sg1 = sem.sem2)
			   inner join gr on (sg.sg1 = gr.gr2)
			   inner join ucgn on (gr.gr1 = ucgn.ucgn2)
			   inner join ucgns on (ucgn.ucgn1 = ucgns.ucgns2)
			   inner join ucxg on (ucgn.ucgn1 = ucxg.ucxg2)
			WHERE ucxg1<30000 and gr13=0 and gr6 is null
				 and sem3=:Y and ucgns5=:Y --year
				 and sem5=:S and ucgns6=:S       --semester
				 and sem4=7                    --course
				 and sp5 >= 2 and sp5 <=8      --faculty
			GROUP BY sg4, sem4, gr7,gr3,gr1, gr19,gr20,gr21,gr22,gr23,gr24,gr25,gr26, sp5)
			ORDER BY ID