import React from 'react'
import {configure, shallow} from 'enzyme'
import Adaptor from 'enzyme-adapter-react-16'
import {Library} from './Library'
import List from "@material-ui/core/List";
import {ListItem} from "@material-ui/core";
import { PlayArrow } from "@material-ui/icons";
import Checkbox from "@material-ui/core/Checkbox";

configure({adapter: new Adaptor()})

describe('<Library/>' , ()=> {
    let wrapper;
   
    beforeEach(() => {
        wrapper = shallow(<Library pt={()=>{}} exercises={[]} selectedVideos={[]}/>)
    });

    it('should render One list for exercises', ()=> {
        
        wrapper.setProps({exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(List)).toHaveLength(1)
    })

    it('should render four listItems when there is only one exercise in the library (title and video)', ()=> {
        
        wrapper.setProps({exercises: [1], selectedVideos: []})
        expect(wrapper.find(ListItem)).toHaveLength(4)
    })

    it('should have two PlayIcon when theres only one exercise in the library', ()=> {
        
        wrapper.setProps({exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(PlayArrow)).toHaveLength(2)
    })

    it('should have two checkbox when theres only one exercise in the library', ()=> {
        
        wrapper.setProps({exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(Checkbox)).toHaveLength(2)
    })

    // it('should render modal when the PlayIcon is clicked', ()=> {
        
    //     wrapper.setProps({exercises: [], selectedVideos: [1],exercise_url: 'https://www.youtube.com/watch?v=o5b0gS7wI1k'})
    //     wrapper.find(PlayArrow).simulate('click')
    //     // expect(wrapper.find(Modal)).toHaveLength(1)
    //     //TODO should just test handleVideoClick??? 

    // })
})
