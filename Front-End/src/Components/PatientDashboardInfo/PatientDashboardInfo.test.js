import React from 'react';
import { configure, shallow} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import {PatientDashboardInfo} from './PatientDashboardInfo'
import { ListItem, Typography } from '@material-ui/core';
import Grid from "@material-ui/core/Grid";
import { PatientInfo } from '../PatientInfo/PatientInfo';
import SearchReport from '../SearchReport/SearchReport';
import PatientVideo from '../PatientVideos/PatientVideo';

configure({adapter: new Adapter()});

describe('<PatientDashboardInfo/>',() => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<PatientDashboardInfo  patient_id={()=>{}} pt={[]} selectedPatient={[]} />)
        
    });
    it('should render zero list of Patient DashbroadInfo',()=> {
        wrapper.setProps({patient_id: 1,pt:[1], selectedPatient: [1]})
        expect(wrapper.find(ListItem)).toHaveLength(0)
    })
    it('should render zero Grid of Patient DashbroadInfo',()=> {
        wrapper.setProps({patient_id: 1,pt:[1], selectedPatient: [1]})
        expect(wrapper.find(Grid)).toHaveLength(0)
    })
    it('should render zero Typography of Patient DashbroadInfo',()=> {
        wrapper.setProps({patient_id: 1,pt:[1], selectedPatient: [1]})
        expect(wrapper.find(Typography)).toHaveLength(0)
    })
    it('should render zero PatientInfo of Patient DashbroadInfo',()=> {
        wrapper.setProps({patient_id: 1,pt:[1], selectedPatient: [1]})
        expect(wrapper.find(PatientInfo)).toHaveLength(0)
    })
    it('should render zero SearchReport of Patient DashbroadInfo',()=> {
        wrapper.setProps({patient_id: 1,pt:[1], selectedPatient: [1]})
        expect(wrapper.find(SearchReport)).toHaveLength(0)
    })
    it('should render zero PatientVideo of Patient DashbroadInfo',()=> {
        wrapper.setProps({patient_id: 1,pt:[1], selectedPatient: [1]})
        expect(wrapper.find(PatientVideo)).toHaveLength(0)
    })
    
})
