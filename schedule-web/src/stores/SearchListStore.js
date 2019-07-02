import { ReduceStore } from 'flux/utils';
import AppDispatcher from '../dispatcher/AppDispatcher';
import { ActionTypes as types } from '../actions/Action-types';
import { loadState } from '../utils/sessionStorage';

const initialState = {
  searchItems: loadState() || [],
  isCollapsed: true,
  isLoading: false,
  isErrored: false,
};

class SearchListStore extends ReduceStore {
  constructor() {
    super(AppDispatcher);
  }

  getInitialState() {
    return initialState;
  }

  reduce(state, action) {
    switch (action.type) {
      case types.FETCH_SEARCH_ITEMS_REQUEST:
        return {
          ...state,
          isLoading: action.isLoading,
        };
      case types.FETCH_SEARCH_ITEMS_SUCCESS:
        return {
          ...state,
          searchItems: action.searchItems,
        };
      case types.FETCH_SEARCH_ITEMS_ERROR:
        return {
          ...state,
          isErrored: action.isErrored,
          error: action.error,
        };
      case types.SEARCH_LIST_OPENED:
        return {
          ...state,
          isCollapsed: action.isCollapsed,
        };
      case types.SEARCH_LIST_CLOSED:
        return {
          ...state,
          isCollapsed: action.isCollapsed,
        };

      default: return state;
    }
  }
}

export default new SearchListStore();
