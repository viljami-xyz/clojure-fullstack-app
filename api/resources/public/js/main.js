import { initSearch } from './search.js';
import { initTask } from './task.js';

document.addEventListener('DOMContentLoaded', () => {
console.log('DOM fully loaded and parsed');
  // Initialize the search functionality
  // Uncomment the line below to enable search functionality
//  initSearch();
  initTask();
    initSearch();
});
