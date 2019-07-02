import roomValidateMapper from './roomValidateMapper';
import {
  orderToTime,
  courseToSubject,
  objNamesToString,
  typeToString,
} from './lessonMappers';


export default lessons => lessons.map(lesson => ({
  time: orderToTime(lesson.order),
  subject: courseToSubject(lesson.course),
  lessonType: typeToString(lesson.course.type),
  teachers: objNamesToString(lesson.course.teachers),
  rooms: roomValidateMapper(objNamesToString(lesson.rooms)),
  groups: objNamesToString(lesson.groups),
}));
