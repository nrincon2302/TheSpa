import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ImageSliderComponent } from './imageSlider.component';
import { SlideInterface } from './types/slide.interface';

describe('ImageSliderComponent', () => {
    let component: ImageSliderComponent;
    let fixture: ComponentFixture<ImageSliderComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ImageSliderComponent],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ImageSliderComponent);
        component = fixture.componentInstance;
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize with default values', () => {
        expect(component.slides).toEqual([]);
        expect(component.currentIndex).toBe(0);
        expect(component.timeoutId).toBeUndefined();
    });

    it('should go to the previous slide correctly', () => {
        const slides: SlideInterface[] = [
            { url: 'slide1.jpg', title: 'Slide 1' },
            { url: 'slide2.jpg', title: 'Slide 2' },
            { url: 'slide3.jpg', title: 'Slide 3' },
        ];
        component.slides = slides;
        component.currentIndex = 1;

        component.goToPrevious();

        expect(component.currentIndex).toBe(0);
    });

    it('should go to the next slide correctly', () => {
        const slides: SlideInterface[] = [
            { url: 'slide1.jpg', title: 'Slide 1' },
            { url: 'slide2.jpg', title: 'Slide 2' },
            { url: 'slide3.jpg', title: 'Slide 3' },
        ];
        component.slides = slides;
        component.currentIndex = 1;

        component.goToNext();

        expect(component.currentIndex).toBe(2);
    });

    it('should go to a specific slide correctly', () => {
        const slides: SlideInterface[] = [
            { url: 'slide1.jpg', title: 'Slide 1' },
            { url: 'slide2.jpg', title: 'Slide 2' },
            { url: 'slide3.jpg', title: 'Slide 3' },
        ];
        component.slides = slides;
        component.currentIndex = 0;

        component.goToSlide(2);

        expect(component.currentIndex).toBe(2);
    });



    it('should return the current slide URL correctly', () => {
        const slides: SlideInterface[] = [
            { url: 'slide1.jpg', title: 'Slide 1' },
            { url: 'slide2.jpg', title: 'Slide 2' },
            { url: 'slide3.jpg', title: 'Slide 3' },
        ];
        component.slides = slides;
        component.currentIndex = 1;

        const currentSlideUrl = component.getCurrentSlideUrl();

        expect(currentSlideUrl).toBe("url('slide2.jpg')");
    });

    it('should return the current slide title correctly', () => {
        const slides: SlideInterface[] = [
            { url: 'slide1.jpg', title: 'Slide 1' },
            { url: 'slide2.jpg', title: 'Slide 2' },
            { url: 'slide3.jpg', title: 'Slide 3' },
        ];
        component.slides = slides;
        component.currentIndex = 1;

        const currentSlideTitle = component.getCurrentSlideTitle();

        expect(currentSlideTitle).toBe('Slide 2');
    });
});
