package com.example.myapplication;
    import java.util.List;



    public class FilterItem {
        private String title;
        private List<String> options;

        public FilterItem(String title, List<String> options) {
            this.title = title;
            this.options = options;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }
    }

