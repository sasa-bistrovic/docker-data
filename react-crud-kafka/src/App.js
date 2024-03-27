import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');

  useEffect(() => {
    fetchMessages();
  }, []);

  const fetchMessages = async () => {
    try {
      const response = await axios.get('/api/messages');
      setMessages(response.data);
    } catch (error) {
      console.error('Error fetching messages:', error);
    }
  };

  const addMessage = async () => {
    try {
      await axios.post('/api/messages', { message: newMessage });
      fetchMessages();
      setNewMessage('');
    } catch (error) {
      console.error('Error adding message:', error);
    }
  };

  return (
    <div>
      <h1>Kafka Messages</h1>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>
            {message}
          </li>
        ))}
      </ul>
      <input
        type="text"
        value={newMessage}
        onChange={(e) => setNewMessage(e.target.value)}
      />
      <button onClick={addMessage}>Add Message</button>
    </div>
  );
}

export default App;
