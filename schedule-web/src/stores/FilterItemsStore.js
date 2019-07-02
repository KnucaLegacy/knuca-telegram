import { ReduceStore } from 'flux/utils';
import AppDispatcher from '../dispatcher/AppDispatcher';
import { ActionTypes as types } from '../actions/Action-types';


const initialState = {
  searchQuery: '',
};

class FilterItemsStore extends ReduceStore {
  constructor() {
    super(AppDispatcher);
  }

  getInitialState() {
    return initialState;
  }

  reduce(state, action) {
    switch (action.type) {
      case types.FILTER_SEARCH_ITEMS:
        return {
          ...state,
          searchQuery: action.searchQuery.toLowerCase(),
        };
      default: return state;
    }
  }
}

export default new FilterItemsStore();
