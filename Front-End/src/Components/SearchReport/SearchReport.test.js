import React from 'react';
import { configure, shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import { SearchReport } from './SearchReport';
import { List } from '@material-ui/core';

configure({ adapter: new Adapter() });
describe('<SearchReport/>', () => {
    let wrapper;
    
    beforeEach(() => {
        
        wrapper = shallow(<SearchReport patients={[]} patient_id={() => { }} pt={[]} selectedPatient={[]} />)
        
    });
    it('should render one list for all patient reports', () => {
        wrapper.setProps({ patient_id: 1, pt: [1], selectedPatient: [1] })
        expect(wrapper.find(List)).toHaveLength(1)
    })
})