import React from 'react';
import PropTypes from 'prop-types';
import { Input, InputGroupAddon, InputGroup } from 'reactstrap';
import ButtonWithPopover from './ButtonWithPopover';


const SearchInput = props => (
  <InputGroup className="main-input" size="lg">
    <Input
        onChange={props.onInputChange}
        onFocus={props.onFocus}
        onBlur={props.onBlur}
        placeholder="Почніть пошук..."
        value={props.searchQuery}
        autoFocus
    />
    <InputGroupAddon addonType="prepend">
      <ButtonWithPopover />
    </InputGroupAddon>
  </InputGroup>
);

SearchInput.propTypes = {
  onInputChange: PropTypes.func,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func,
  searchQuery: PropTypes.string,
};

export default SearchInput;
