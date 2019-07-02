import { dateToWeekDay } from './lessonMappers';
import getTodayDate from './getTodayDate';

export default (date = getTodayDate()) => dateToWeekDay(date);
