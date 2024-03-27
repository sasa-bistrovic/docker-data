import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState({ name: '', price: '' });
  const [updateItem, setUpdateItem] = useState({ id: '', name: '', price: '' });
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      const response = await axios.get('/items');
      setItems(response.data);
    } catch (error) {
      console.error('Error fetching items:', error);
    }
  };

  const handleAddItem = async () => {
    try {
      await axios.post('/items', newItem);
      fetchItems();
      setNewItem({ name: '', price: '' });
    } catch (error) {
      console.error('Error adding item:', error);
    }
  };

  const handleUpdateItem = async () => {
    try {
      await axios.put(`/items/${updateItem.id}`, updateItem);
      fetchItems();
      setUpdateItem({ id: '', name: '', price: '' });
      setIsEditing(false);
    } catch (error) {
      console.error('Error updating item:', error);
    }
  };

  const handleDeleteItem = async (id) => {
    try {
      await axios.delete(`/items/${id}`);
      fetchItems();
    } catch (error) {
      console.error('Error deleting item:', error);
    }
  };

  const handleEditItem = (item) => {
    setUpdateItem(item);
    setIsEditing(true);
  };

  return (
    <div>
      <h1>Redis Item</h1>

      {!isEditing && (
        <div>
          <h2>Add New Item</h2>
          <input
            type="text"
            placeholder="Name"
            value={newItem.name}
            onChange={(e) => setNewItem({ ...newItem, name: e.target.value })}
          />
          <input
            type="text"
            placeholder="Price"
            value={newItem.price}
            onChange={(e) => setNewItem({ ...newItem, price: e.target.value })}
          />
          <button onClick={handleAddItem}>Add Item</button>
        </div>
      )}

      {isEditing && (
        <div>
          <h2>Edit Item</h2>
          <input
            type="text"
            placeholder="Name"
            value={updateItem.name}
            onChange={(e) => setUpdateItem({ ...updateItem, name: e.target.value })}
          />
          <input
            type="text"
            placeholder="Price"
            value={updateItem.price}
            onChange={(e) => setUpdateItem({ ...updateItem, price: e.target.value })}
          />
          <button onClick={handleUpdateItem}>Update Item</button>
          <button onClick={() => setIsEditing(false)}>Cancel</button>
        </div>
      )}

      <h2>Items</h2>
      <ul>
        {items.map((item) => (
          <li key={item.id}>
            {item.name} - ${item.price}
            <p>
              <button onClick={() => handleDeleteItem(item.id)}>Delete</button>
              <button onClick={() => handleEditItem(item)}>Edit</button>
            </p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;