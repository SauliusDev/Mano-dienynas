package com.sunnyoaklabs.manodienynas.data.remote

object HttpRoutes {

    private const val BASE_URL = "https://www.manodienynas.lt"

    const val EVENTS_GET = "$BASE_URL/1/lt/page/sf/resolve_post/event/list"

    const val MARKS_GET = "$BASE_URL/1/lt/page/marks_pupil/marks"

    const val ATTENDANCE_GET = "$BASE_URL/1/lt/page/marks_pupil/pupil_attendance"

    const val CLASS_WORK_GET = "$BASE_URL/1/lt/page/classhomework/class_work"
    const val CLASS_WORK_POST = "$BASE_URL/1/lt/page/classhomework/class_work/{page}"

    const val HOME_WORK_GET = "$BASE_URL/1/lt/page/classhomework/home_work"
    const val HOME_WORK_POST = "$BASE_URL/1/lt/page/classhomework/home_work/{page}"

    const val CONTROL_WORK_GET = "$BASE_URL/1/lt/page/control_work/dates_pupil"
    const val CONTROL_WORK_POST = "$BASE_URL/1/lt/page/control_work/dates_pupil/{group_id}"

    const val TERM_GET = "$BASE_URL/1/lt/page/termmark/pupil_term"
    const val TERM_LEGEND_GET = "$BASE_URL/1/lt/ajax/popup/show_term_legend"

    const val MESSAGE_GOTTEN_LIST_GET = "$BASE_URL/1/lt/page/message_new/message_list"
    const val MESSAGE_SENT_LIST_GET = "$BASE_URL/1/lt/page/message_new/sent_list"
    const val MESSAGE_STARRED_LIST_GET = "$BASE_URL/1/lt/page/message_new/starred_list"
    const val MESSAGE_DELETED_LIST_GET = "$BASE_URL/1/lt/page/message_new/deleted_list"
    const val MESSAGE_INDIVIDUAL_GET = "$BASE_URL/1/lt/page/message_new/message/{message_id}"

    const val HOLIDAY_GET = "$BASE_URL/1/lt/page/atostogos/atostogu_rodymas"

    const val PARENT_MEETINGS_GET = "$BASE_URL/1/lt/page/sf/resolve_post/parent/meetings/list"

    const val SCHEDULE_GET = "$BASE_URL/1/lt/page/schedule/view"

    const val CALENDAR_GET = "$BASE_URL/1/lt/page/sf/resolve/calendar/show"
    const val CALENDAR_DATE_GET = "$BASE_URL/fc-load-events?start={start_date}&end={end_date}"

}