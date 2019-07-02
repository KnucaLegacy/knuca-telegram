import { ActionTypes as types } from './Action-types';
import { getItems } from '../api/SearchItemsDataManager';
import AppDispatcher from '../dispatcher/AppDispatcher';
import mapItemsResponseToArray from '../utils/mapItemsResponseToArray';


const searchItemsFetchSuccess = searchItems => ({
  type: types.FETCH_SEARCH_ITEMS_SUCCESS,
  searchItems,
});

const searchItemsIsLoading = bool => ({
  type: types.FETCH_SEARCH_ITEMS_REQUEST,
  isLoading: bool,
});

const searchItemsIsErrored = error => ({
  type: types.FETCH_SEARCH_ITEMS_ERROR,
  isErrored: true,
  error,
});

export const openSearchList = () => AppDispatcher.dispatch({
  type: types.SEARCH_LIST_OPENED,
  isCollapsed: true,
});

export const closeSearchList = () => AppDispatcher.dispatch({
  type: types.SEARCH_LIST_CLOSED,
  isCollapsed: false,
});

export const searchItems = query => AppDispatcher.dispatch({
  type: types.FILTER_SEARCH_ITEMS,
  searchQuery: query,
});

export async function fetchSearchItems() {
  try {
    AppDispatcher.dispatch(searchItemsIsLoading(true));

    const itemsResponse = await getItems();
    const items = mapItemsResponseToArray(itemsResponse);

    AppDispatcher.dispatch(searchItemsIsLoading(false));
    AppDispatcher.dispatch(searchItemsFetchSuccess(items));
  } catch (error) {
    AppDispatcher.dispatch(searchItemsIsLoading(false));
    AppDispatcher.dispatch(searchItemsIsErrored(error));
  }
}
