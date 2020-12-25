import React from 'react';
import { configure, shallow} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import {PatientInfo} from './PatientInfo';
import {
    Divider,
    ListItem,
    ListItemText,
    ListSubheader,
  } from "@material-ui/core";
  import List from "@material-ui/core/List";

configure({adapter: new Adapter()});

describe('<PatientInfo/>',() => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<PatientInfo patient_id={()=>{}} info={[]} selectedPatient={[]} />);
    });

    it('should render one list of patients',()=> {
        wrapper.setProps({patient_id: 1,info: [1], selectedPatient: [1]})
        expect(wrapper.find(List)).toHaveLength(1)
    })
    it('should render one listItem of patients component',()=> {
        wrapper.setProps({patient_id: 1,info: [1], selectedPatient: [1]})
        expect(wrapper.find(ListItem)).toHaveLength(5)
    })
    it('should render one listItemText of patients component',()=> {
        wrapper.setProps({patient_id: 1,info: [1], selectedPatient: [1]})
        expect(wrapper.find(ListItemText)).toHaveLength(4)
    })
})


