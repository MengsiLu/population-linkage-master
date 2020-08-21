package uk.ac.standrews.cs.population_linkage.supportClasses;

public class RecordDetailList {
        //int id_field_index;
        private int field_index;
        private String RecordPair;
        private String record1_id;
        private String record2_id;
        private String field_value1;
        private String field_value2;
        private String metric_name;
        private String pair_distance;
        private String LinkStatus;


        public RecordDetailList(int field_index, String RecordPair, String record1_id, String record2_id, String field_value1, String field_value2, String metric_name, String pair_distance , String LinkStatus){
            //this.id_field_index = id_field_index;
            this.field_index = field_index;
            this.RecordPair = RecordPair;
            this.record1_id = record1_id;
            this.record2_id = record2_id;
            this.field_value1 = field_value1;
            this.field_value2 = field_value2;
            this.metric_name = metric_name;
            this.pair_distance = pair_distance;
            this.LinkStatus = LinkStatus;

            //this.total_distance = total_distance;


        }

        public boolean equals(Object obj) {
            RecordDetailList t = (RecordDetailList) obj;
            //return RecordPair.equals(t.RecordPair) && record1_id.equals(t.record1_id) && record2_id.equals(t.record2_id) && metric_name.equals(t.metric_name);
            return RecordPair.equals(t.RecordPair) && record1_id.equals(t.record1_id) && record2_id.equals(t.record2_id) && metric_name.equals(t.metric_name);
        }

        public int hashCode() {
            String str = RecordPair + record1_id + record2_id + metric_name;
            return str.hashCode();
        }


        public int getField_index() {
            return field_index;
        }

        public void setField_index(int field_index) {
            this.field_index = field_index;
        }

        public String getRecordPair() {
            return RecordPair;
        }

        public void setRecordPair(String recordPair) {
            RecordPair = recordPair;
        }

        public String getRecord1_id() {
            return record1_id;
        }

        public void setRecord1_id(String record1_id) {
            this.record1_id = record1_id;
        }

        public String getRecord2_id() {
            return record2_id;
        }

        public void setRecord2_id(String record2_id) {
            this.record2_id = record2_id;
        }

        public String getField_value1() {
            return field_value1;
        }

        public void setField_value1(String field_value1) {
            this.field_value1 = field_value1;
        }

        public String getField_value2() {
            return field_value2;
        }

        public void setField_value2(String field_value2) {
            this.field_value2 = field_value2;
        }

        public String getMetric_name() {
            return metric_name;
        }

        public void setMetric_name(String metric_name) {
            this.metric_name = metric_name;
        }

        public String getPair_distance() {
            return pair_distance;
        }

        public void setPair_distance(String pair_distance) {
            this.pair_distance = pair_distance;
        }

        public String getLinkStatus() {
            return LinkStatus;
        }

        public void setLinkStatus(String linkStatus) {
            LinkStatus = linkStatus;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("RecordPair:" + RecordPair);
            builder.append("Record1_id:" + record1_id);
            builder.append("Record2_id:" + record2_id);
            builder.append("field_value1:" + field_value1);
            builder.append("field_value2:" + field_value2);
            return  builder.toString();
        }



}
