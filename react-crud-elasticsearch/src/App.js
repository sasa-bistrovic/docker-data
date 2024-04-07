import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [books, setBooks] = useState([]);
  const [newBook, setNewBook] = useState({ title: '', author: '' });
  const [updateBook, setUpdateBook] = useState({ id: '', title: '', author: '' });
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('/books');
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  const handleAddBook = async () => {
    try {
      await axios.post('/books', newBook);
      fetchBooks();
      setNewBook({ title: '', author: '' });
    } catch (error) {
      console.error('Error adding book:', error);
    }
  };

  const handleUpdateBook = async () => {
    try {
      await axios.put(`/books/${updateBook.id}`, updateBook);
      fetchBooks();
      setUpdateBook({ id: '', title: '', author: '' });
      setIsEditing(false);
    } catch (error) {
      console.error('Error updating book:', error);
    }
  };

  const handleDeleteBook = async (id) => {
    try {
      await axios.delete(`/books/${id}`);
      fetchBooks();
    } catch (error) {
      console.error('Error deleting book:', error);
    }
  };

  const handleEditBook = (book) => {
    setUpdateBook(book);
    setIsEditing(true);
  };

  return (
    <div>
      <h1>Elasticsearch Book</h1>

      {!isEditing && (
        <div>
          <h2>Add Book</h2>
          <input
            type="text"
            placeholder="Title"
            value={newBook.title}
            onChange={(e) => setNewBook({ ...newBook, title: e.target.value })}
          />
          <input
            type="text"
            placeholder="Author"
            value={newBook.author}
            onChange={(e) => setNewBook({ ...newBook, author: e.target.value })}
          />
          <button onClick={handleAddBook}>Add Book</button>
        </div>
      )}

      {isEditing && (
        <div>
          <h2>Edit Book</h2>
          <input
            type="text"
            placeholder="Title"
            value={updateBook.title}
            onChange={(e) => setUpdateBook({ ...updateBook, title: e.target.value })}
          />
          <input
            type="text"
            placeholder="Author"
            value={updateBook.author}
            onChange={(e) => setUpdateBook({ ...updateBook, author: e.target.value })}
          />
          <button onClick={handleUpdateBook}>Update Book</button>
          <button onClick={() => setIsEditing(false)}>Cancel</button>
        </div>
      )}

      <h2>Books</h2>
      <ul>
        {books.map((book) => (
          <li key={book.id}>
            {book.title} by {book.author}
            <p>
              <button onClick={() => handleDeleteBook(book.id)}>Delete</button>
              <button onClick={() => handleEditBook(book)}>Edit</button>
            </p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
