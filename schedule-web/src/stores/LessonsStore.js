import { ReduceStore } from 'flux/utils';
import AppDispatcher from '../dispatcher/AppDispatcher';
import { ActionTypes as types } from '../actions/Action-types';


const initialState = {
  lessons: [],
  searchItem: {
    name: '',
  },
  isLoading: false,
  isFetched: false,
  isErrored: false,
  isMany: false,
};

class LessonsStore extends ReduceStore {
  constructor() {
    super(AppDispatcher);
  }

  getInitialState() {
    return initialState;
  }

  reduce(state, action) {
    switch (action.type) {
      case types.FETCH_LESSONS_REQUEST:
        return {
          ...state,
          isLoading: action.isLoading,
        };
      case types.FETCH_LESSONS_SUCCESS:
        return {
          ...state,
          lessons: action.lessons,
          searchItem: action.searchItem,
          isFetched: action.isFetched,
          isMany: action.isMany,
          date: action.date,
        };
      case types.FETCH_LESSONS_ERROR:
        return {
          ...state,
          isErrored: action.isErrored,
          error: action.error,
        };
      default: return state;
    }
  }
}

export default new LessonsStore();
