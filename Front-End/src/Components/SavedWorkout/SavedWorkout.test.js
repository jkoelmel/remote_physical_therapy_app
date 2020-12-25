import React from 'react';
import {configure, shallow} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import {SavedWorkout} from './SavedWorkout';
import List from "@material-ui/core/List";
import { ListItem, ListSubheader, ListItemText, ListItemSecondaryAction } from '@material-ui/core';

configure({adapter: new Adapter()});

describe('<SavedWorkout/>',()=> {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<SavedWorkout pt_id={()=>{}} selectedWorkouts={[]} />);
    });

    it('should render two list of selected Workout',()=> {
        wrapper.setProps({pt_id: 1, selectedWorkouts: [1]})
        expect(wrapper.find(List)).toHaveLength(2)
    })
    it('should render zero listSubHeader of selected Workout',()=> {
        wrapper.setProps({pt_id: 1, selectedWorkouts: [1]})
        expect(wrapper.find(ListSubheader)).toHaveLength(0)
    })
    it('should render zero listItem of selected Workout',()=> {
        wrapper.setProps({pt_id: 1, selectedWorkouts: [1]})
        expect(wrapper.find(ListItem)).toHaveLength(0)
    })
    it('should render zero listItemText of selected Workout',()=> {
        wrapper.setProps({pt_id: 1, selectedWorkouts: [1]})
        expect(wrapper.find(ListItemText)).toHaveLength(0)
    })
    it('should render zero ListItemSecondaryAction of selected Workout',()=> {
        wrapper.setProps({pt_id: 1, selectedWorkouts: [1]})
        expect(wrapper.find(ListItemSecondaryAction)).toHaveLength(0)
    })
})
