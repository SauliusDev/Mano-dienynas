package com.sunnyoaklabs.manodienynas.data.remote

object HttpRoutes {

    private const val BASE_URL = "https://www.manodienynas.lt"
   
    const val LOGIN_POST = "$BASE_URL/1/lt/ajax/user/login"

    const val LOGOUT_GET = "$BASE_URL/1/lt/action/user/logout"

    /**
     * 1-2365-23/1 - saules
     * 1-2158-23/1 - muzikos
     * **/
    const val CHANGE_ROLE_GET = "$BASE_URL/1/lt/action/user/change_role/1-{school_id}-23/1"

    const val EVENTS_POST = "$BASE_URL/event/list"
    const val EVENTS_GET = "$BASE_URL/1/lt/page/sf/resolve_post/event/list"
    const val EVENTS_INDIVIDUAL_GET = "$BASE_URL{mark_url}"

    const val MARKS_GET = "$BASE_URL/1/lt/page/marks_pupil/marks"
    const val MARKS_POST = "$BASE_URL/1/lt/page/marks_pupil/marks/{school_id}"

    const val ATTENDANCE_GET = "$BASE_URL/1/lt/page/marks_pupil/pupil_attendance"

    const val CLASS_WORK_GET = "$BASE_URL/1/lt/page/classhomework/class_work"
    const val CLASS_WORK_POST = "$BASE_URL/1/lt/page/classhomework/class_work/{page}"

    const val HOME_WORK_GET = "$BASE_URL/1/lt/page/classhomework/home_work"
    const val HOME_WORK_POST = "$BASE_URL/1/lt/page/classhomework/home_work/0/{page}"

    const val CONTROL_WORK_GET = "$BASE_URL/1/lt/page/control_work/dates_pupil"
    const val CONTROL_WORK_POST = "$BASE_URL/1/lt/page/control_work/dates_pupil/{group_id}"

    const val TERM_GET = "$BASE_URL/1/lt/page/termmark/pupil_term"

    const val MESSAGE_GOTTEN_LIST_GET = "$BASE_URL/1/lt/page/message_new/message_list"
    const val MESSAGE_SENT_LIST_GET = "$BASE_URL/1/lt/page/message_new/sent_list"
    const val MESSAGE_STARRED_LIST_GET = "$BASE_URL/1/lt/page/message_new/starred_list"
    const val MESSAGE_DELETED_LIST_GET = "$BASE_URL/1/lt/page/message_new/deleted_list"
    const val MESSAGE_INDIVIDUAL_GET = "$BASE_URL/1/lt/page/message_new/message/{message_id}"
    //const val MESSAGE_INDIVIDUAL_FILE_GET = "$BASE_URL{message_url}"
    const val MESSAGE_GOTTEN_LIST_PAGE_GET = "$BASE_URL/1/lt/page/message_new/message_list/date_desc/{page}"
    const val MESSAGE_SENT_LIST_PAGE_GET = "$BASE_URL/1/lt/page/message_new/sent_list/date_desc/{page}"
    const val MESSAGE_STARRED_LIST_PAGE_GET = "$BASE_URL/1/lt/page/message_new/starred_list/date_desc/{page}"
    const val MESSAGE_DELETED_LIST_PAGE_GET = "$BASE_URL/1/lt/page/message_new/deleted_list/date_desc/{page}"
    //const val MESSAGE_DELETE_GET = "$BASE_URL/1/lt/ajax/message_new/delete_messages/{message_id}"
    //const val MESSAGE_REPLY_GET = "$BASE_URL/1/lt/page/message_new/message/{message_id}/{message_id}/#message-anchor-id-{message_id}"
    //const val MESSAGE_FWD_GET = "$BASE_URL/1/lt/page/message_new/new_message/fwd-{message_id}"

    const val HOLIDAY_GET = "$BASE_URL/1/lt/page/atostogos/atostogu_rodymas"

    const val PARENT_MEETINGS_GET = "$BASE_URL/1/lt/page/sf/resolve_post/parent/meetings/list"

    const val SCHEDULE_GET = "$BASE_URL/1/lt/page/schedule/view"

    const val CALENDAR_GET = "$BASE_URL/fc-load-events"
    const val CALENDAR_EVENT_GET = "$BASE_URL/calendar/show/{event_id}" // 3/88190

}