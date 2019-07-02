/* eslint-disable */
import axios from 'axios';
import config from '../config/config';

export async function getLessons(endPoint) {
  const { serverApiUrl } = config;
  const url = `${serverApiUrl}/${endPoint}`;
  
  const lessons = await axios(url);
    
  return lessons.data;
}
