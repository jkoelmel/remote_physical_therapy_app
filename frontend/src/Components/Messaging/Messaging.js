import React, {useEffect} from 'react';
import {
  addResponseMessage,
  addUserMessage,
  deleteMessages,
  Widget,
} from 'react-chat-widget';
import 'react-chat-widget/lib/styles.css';
import axios from 'axios';
import {connect} from 'react-redux';
import './Messaging.css';
import {fetchPTsPatients} from '../../Redux/actions/actions-pt';

const Messaging = (props) => {
  useEffect(() => {
    deleteMessages(1000);
    fetchMessages();
  }, [props.pt.selectedPatient.patient_id]);

  const fetchMessages = () => {
    axios
      .get('api/pt/message/id', {
        params: {
          pt: props.pt.pt_id,
          patient: props.pt.selectedPatient.patient_id,
        },
      })
      .then((response) => {
        response.data.map((message) => {
          if (message.sender === props.pt.email) {
            addUserMessage(message.message);
          } else {
            addResponseMessage(message.message);
          }
        });
      })
      .catch((err) => console.log(err));
  };

  const handleNewUserMessage = (newMessage) => {
    const params = new URLSearchParams();
    params.append('message', newMessage);
    params.append('pt', props.pt.pt_id);
    params.append('patient', props.pt.selectedPatient.patient_id);

    axios
      .post('api/pt/message/register', params)
      .then((response) => {
        if (response.data === 200) {
          console.log('Message success');
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <div>
      <Widget
        title={
          props.pt.selectedPatient.patient_id
            ? 'Messages From'
            : 'Message Center'
        }
        subtitle={
          props.pt.selectedPatient.patient_id
            ? `${props.pt.selectedPatient.f_name} ${props.pt.selectedPatient.l_name}`
            : 'Choose a patient'
        }
        showTimeStamp={false}
        senderPlaceHolder={'Enter message...'}
        handleNewUserMessage={handleNewUserMessage}
      />
    </div>
  );
};

export default connect(
  (state) => ({
    pt: state.pt,
  }),
  (dispatch) => ({
    fetchPTsPatients: (pt_id) => dispatch(fetchPTsPatients(pt_id)),
  }),
)(Messaging);
