import React, { Component } from 'react'
import {configure, shallow} from 'enzyme'
import Adaptor from 'enzyme-adapter-react-16'
import {AssignWorkout} from './AssignWorkout'
import List from "@material-ui/core/List";
import {ListItem} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import Checkbox from "@material-ui/core/Checkbox";

configure({adapter: new Adaptor()})

describe('<AssignWorkout/>' , ()=> {
    let wrapper;
    let mockFunc;
    beforeEach(() => {
        mockFunc = jest.fn()
        wrapper = shallow(<AssignWorkout pt={()=>{}} patients={[]} selectedWorkouts={[]} />)
      });

      it('should render one list for patients to assign workouts to', ()=> {
        
        wrapper.setProps({exercises: [1], patients: [1] ,selectedWorkouts: [1]})
        expect(wrapper.find(List)).toHaveLength(1)
    })

    it('should render one "Assign" button for selected patient', ()=> {
        
        wrapper.setProps({exercises: [1], patients: [1] ,selectedWorkouts: [1]})
        expect(wrapper.find(Button)).toHaveLength(1)
    })

    it('should render checkbox for each patient in the list', ()=> {
        
        wrapper.setProps({exercises: [1], patients: [1] ,selectedWorkouts: [1]})
        expect(wrapper.find(Checkbox)).toHaveLength(1)
    })

    //this particular test reasures props.assignWorkout is called
    it('number of times assignWorkout is called should be equal to the number of times it was simulately clicked', ()=> {
        
        wrapper.setProps({assignWorkout: mockFunc })
        
        wrapper.find(Button).simulate('click') //button is clicked once so length of callback should equal 1
        const callback = mockFunc.mock.calls.length
        expect(callback).toBe(1)
    })

    })