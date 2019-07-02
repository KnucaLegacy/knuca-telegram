import React, { Component } from 'react';
import { Container } from 'flux/utils';

import SearchList from '../components/SearchList';
import SearchInput from '../components/SearchInput';

import SearchListStore from '../stores/SearchListStore';
import FilterItemsStore from '../stores/FilterItemsStore';

import { fetchLessons } from '../actions/LessonsActions';
import {
  fetchSearchItems,
  searchItems,
  openSearchList,
  closeSearchList,
} from '../actions/SearchItemsActions';


function onInputChange(event) {
  searchItems(event.target.value);
}

function onFocus() {
  openSearchList();
}

function onBlur() {
  setTimeout(closeSearchList, 400);
}

class SearchContainer extends Component {
  static getStores() {
    return [
      SearchListStore,
      FilterItemsStore,
    ];
  }

  static calculateState() {
    const searchState = SearchListStore.getState();
    const { searchQuery } = FilterItemsStore.getState();

    return {
      searchState,
      searchQuery,

      fetchSearchItems,
      fetchLessons,

      onInputChange,
      onFocus,
      onBlur,
    };
  }

  render() {
    return (
      <React.Fragment>
        <SearchInput
            onBlur={this.state.onBlur}
            onFocus={this.state.onFocus}
            onInputChange={this.state.onInputChange}
            searchQuery={this.state.searchQuery}
        />
        <SearchList
            searchState={this.state.searchState}
            searchQuery={this.state.searchQuery}
            fetchLessons={this.state.fetchLessons}
            fetchSearchItems={this.state.fetchSearchItems}
        />
      </React.Fragment>
    );
  }
}


export default Container.create(SearchContainer);
