// order to time mapper
const orderTimeMap = {
  FIRST: '09:00',
  SECOND: '10:30',
  THIRD: '12:20',
  FOURTH: '13:50',
  FIFTH: '15:20',
  SIXTH: '16:50',
  SEVENTH: '18:20',
};

export const orderToTime = order => orderTimeMap[order];
/*
Example:
  input:
    [
      {name: 'hello'},
      {name: 'world},
    ]
  output:
    'hello, world'
*/
export const objNamesToString = arr => arr.map(obj => obj.name).join(', ');

// Type to string mapper
const lessonTypesMap = {
  LECTURE: 'Лекція',
  PRACTICE: 'Практика',
  LAB: 'Лабораторна',
  EXAM: 'Екзамен',
  CONSULTATION: 'Консультація',
  FACULTY: 'Факультатив',
  INDIVIDUAL: 'Індивідуальне заняття',
};

export const typeToString = type => lessonTypesMap[type];

// course to subject name
export const courseToSubject = course => course.subject.name;

// date to day mapper
const daysMap = {
  0: 'Неділя',
  1: 'Понеділок',
  2: 'Вівторок',
  3: 'Середа',
  4: 'Четвер',
  5: 'П\'ятниця',
  6: 'Суббота',
};

export const dateToWeekDay = date => `${daysMap[(new Date(date).getDay())]} - ${date}`;
