import { ActionTypes as types } from './Action-types';
import { getLessons } from '../api/LessonsDataManager';
import AppDispatcher from '../dispatcher/AppDispatcher';
import getTodayDate from '../utils/getTodayDate';


const lessonsFetchSuccess = (lessons, searchItem, date, isMany) => AppDispatcher.dispatch({
  type: types.FETCH_LESSONS_SUCCESS,
  lessons,
  searchItem,
  date,
  isMany,
  isFetched: true,
});

const lessonsIsLoading = bool => AppDispatcher.dispatch({
  type: types.FETCH_LESSONS_REQUEST,
  isLoading: bool,
  isFetched: !bool,
});

const lessonsIsErrored = error => AppDispatcher.dispatch({
  type: types.FETCH_LESSONS_ERROR,
  isErrored: true,
  isFetched: false,
  error,
});

export async function fetchLessons(url, searchItem, date = getTodayDate(), isMany = false) {
  try {
    lessonsIsLoading(true);
    const lessons = await getLessons(url);

    lessonsIsLoading(false);
    lessonsFetchSuccess(lessons, searchItem, date, isMany);
  } catch (error) {
    lessonsIsLoading(false);
    lessonsIsErrored(error);
  }
}
