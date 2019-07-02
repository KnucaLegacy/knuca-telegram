import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { ListGroup } from 'reactstrap';
import SearchListItem from './SearchListItem';
import ErrorListItem from './ErrorListItem';
import Spinner from './Spinner';

export default class SearchList extends PureComponent {
  static propTypes = {
    searchState: PropTypes.shape({
      isLoading: PropTypes.bool,
      isErrored: PropTypes.bool,
      searchItems: PropTypes.oneOfType([PropTypes.array, PropTypes.object]),
    }),
    searchQuery: PropTypes.string,
    fetchLessons: PropTypes.func,
    fetchSearchItems: PropTypes.func,
  }

  componentDidMount() {
    if (this.props.searchState.searchItems.length === 0) {
      this.props.fetchSearchItems();
    }
  }

  renderSearchItems = () => {
    const {
      fetchLessons,
      searchQuery,
      searchState: {
        isCollapsed,
        searchItems,
      },
    } = this.props;

    if (searchQuery.length >= 1 && isCollapsed) {
      const filteredItems = searchItems
        .filter(item => item.name.toLowerCase().includes(searchQuery));

      return (
        filteredItems.map(item =>
          (<SearchListItem
              item={item}
              key={item.name}
              fetchLessons={fetchLessons}
          />),
        )
      );
    }
    return null;
  }


  render() {
    const { searchState: { isLoading, isErrored } } = this.props;

    if (isLoading) {
      return <div className="loading-state col-12"><Spinner /></div>;
    }


    return (
      <ListGroup className="search-result-list col-12">
        { isErrored ? <ErrorListItem /> :
          this.renderSearchItems()
        }
      </ListGroup>);
  }
}
