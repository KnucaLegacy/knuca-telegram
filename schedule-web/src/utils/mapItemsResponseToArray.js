import { saveState } from './sessionStorage';

const mapItemsResponseToArray = (searchItems) => {
  const { groups } = searchItems;
  let items = [];

  if (
    groups.length > 0 &&
    items.length === 0
  ) {
    Object.keys(searchItems).forEach((key) => {
      items = items.concat(searchItems[key].map(item => ({
        ...item,
        type: key.slice(0, -1),
      })));
    });

    saveState(items);
  }
  return items;
};

export default mapItemsResponseToArray;
