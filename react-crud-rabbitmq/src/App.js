import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');

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

  const sendMessage = async () => {
    try {
      await axios.post('/api/messages', { message: inputMessage });
      setInputMessage('');
      fetchMessages();
    } catch (error) {
      console.error('Error sending message:', error);
    }
  };

  return (
    <div>
      <h1>RabbitMQ Messages</h1>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>
            {message}
          </li>
        ))}
      </ul>
      <input
        type="text"
        value={inputMessage}
        onChange={(e) => setInputMessage(e.target.value)}
      />
      <button onClick={sendMessage}>Add Message</button>
    </div>
  );
}

export default App;