import React from 'react';
import { configure, shallow} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import {ActivitySummary} from './ActivitySummary';
import { List,ListItem } from '@material-ui/core';


configure({adapter: new Adapter()});

describe('<ActivitySummary/>',() => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<ActivitySummary patient={()=>{}} pt={()=>{}} selectedPatient={[]} />)
        
    });

    it('should render one list of Activity Summary',()=> {
        wrapper.setProps({patient: 1,pt: 1, selectedPatient: [1]})
        expect(wrapper.find(List)).toHaveLength(1)
    })
    it('should render one listItem of Activity Summary',()=> {
        wrapper.setProps({patient: 1,pt: 1, selectedPatient: [1]})
        expect(wrapper.find(ListItem)).toHaveLength(1)
    })
})