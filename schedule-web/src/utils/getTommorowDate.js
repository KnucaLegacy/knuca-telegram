const getTommorowDate = () => new Date(Date.now() + 86400000).toISOString().substring(0, 10);

export default getTommorowDate;

