# =============================================================================
# Vehicle Service Management System - Interactive Simulation (PowerShell)
# Course: 25ITT32 Java Programming | Scenario 16 (Reg: 16, 46, 76, 106)
# =============================================================================

$ErrorActionPreference = "SilentlyContinue"

# ---- In-Memory Data Store ----
$script:customers      = @()
$script:vehicles       = @()
$script:appointments   = @()
$script:serviceRecords = @()
$script:spareParts     = @()
$script:bills          = @()
$script:nextCustId     = 1
$script:nextVehId      = 1
$script:nextApptId     = 1
$script:nextSvcId      = 1
$script:nextBillId     = 1
$script:nextPartId     = 1

function Initialize-Data {
    $c1 = [PSCustomObject]@{ Id=1; Name="Arjun Kumar";   Phone="9876543210"; Email="arjun@email.com";  Address="12 Anna Nagar, Chennai" }
    $c2 = [PSCustomObject]@{ Id=2; Name="Priya Sharma";  Phone="9845678901"; Email="priya@email.com";  Address="45 Gandhi Road, Coimbatore" }
    $c3 = [PSCustomObject]@{ Id=3; Name="Ravi Sundaram"; Phone="9765432100"; Email="ravi@email.com";   Address="8 MG Road, Erode" }
    $c4 = [PSCustomObject]@{ Id=4; Name="Meena Devi";    Phone="9988776655"; Email="meena@email.com";  Address="23 Lake View, Tirupur" }
    $c5 = [PSCustomObject]@{ Id=5; Name="Suresh Babu";   Phone="9123456789"; Email="suresh@email.com"; Address="7 Raja Street, Salem" }
    $script:customers  = @($c1,$c2,$c3,$c4,$c5)
    $script:nextCustId = 6

    $v1 = [PSCustomObject]@{ Id=1; CustId=1; RegNo="TN33AB1234"; Brand="Honda";  Model="City";    Type="CAR";        Year=2020 }
    $v2 = [PSCustomObject]@{ Id=2; CustId=1; RegNo="TN33CD5678"; Brand="TVS";    Model="Apache";  Type="MOTORCYCLE"; Year=2021 }
    $v3 = [PSCustomObject]@{ Id=3; CustId=2; RegNo="TN22EF9012"; Brand="Toyota"; Model="Innova";  Type="CAR";        Year=2019 }
    $v4 = [PSCustomObject]@{ Id=4; CustId=3; RegNo="TN44GH3456"; Brand="Bajaj";  Model="Pulsar";  Type="MOTORCYCLE"; Year=2022 }
    $v5 = [PSCustomObject]@{ Id=5; CustId=4; RegNo="TN55IJ7890"; Brand="Maruti"; Model="Swift";   Type="CAR";        Year=2018 }
    $script:vehicles   = @($v1,$v2,$v3,$v4,$v5)
    $script:nextVehId  = 6

    $p1 = [PSCustomObject]@{ Id=1; Name="Engine Oil (1L)"; Category="Lubricants"; UnitPrice=350.0; Stock=50 }
    $p2 = [PSCustomObject]@{ Id=2; Name="Oil Filter";      Category="Filters";    UnitPrice=180.0; Stock=30 }
    $p3 = [PSCustomObject]@{ Id=3; Name="Air Filter";      Category="Filters";    UnitPrice=250.0; Stock=25 }
    $p4 = [PSCustomObject]@{ Id=4; Name="Brake Pad Set";   Category="Brakes";     UnitPrice=850.0; Stock=20 }
    $p5 = [PSCustomObject]@{ Id=5; Name="Spark Plug";      Category="Ignition";   UnitPrice=120.0; Stock=40 }
    $p6 = [PSCustomObject]@{ Id=6; Name="Coolant (1L)";    Category="Fluids";     UnitPrice=200.0; Stock=35 }
    $script:spareParts = @($p1,$p2,$p3,$p4,$p5,$p6)
    $script:nextPartId = 7

    $s1 = [PSCustomObject]@{ Id=1; ApptId=0; VehId=1; Date="2026-10-01"; Work="Full service + Oil change"; Labor=800.0;  PartsCost=530.0; Status="COMPLETED" }
    $s2 = [PSCustomObject]@{ Id=2; ApptId=0; VehId=3; Date="2026-10-05"; Work="Brake pad replacement";     Labor=500.0;  PartsCost=850.0; Status="COMPLETED" }
    $s3 = [PSCustomObject]@{ Id=3; ApptId=0; VehId=4; Date="2026-10-12"; Work="Spark plug + Air filter";   Labor=300.0;  PartsCost=370.0; Status="COMPLETED" }
    $script:serviceRecords = @($s1,$s2,$s3)
    $script:nextSvcId = 4
}

function Write-Header($text) {
    Write-Host ""
    Write-Host "  ============================================================" -ForegroundColor Cyan
    Write-Host ("   " + $text) -ForegroundColor Yellow
    Write-Host "  ============================================================" -ForegroundColor Cyan
}

function Write-Sep { Write-Host "  ------------------------------------------------------------" -ForegroundColor DarkGray }

function Pause-Continue {
    Write-Host ""
    Write-Host "  Press Enter to continue..." -ForegroundColor DarkYellow
    Read-Host | Out-Null
}

function Get-CustomerById($id) { $script:customers   | Where-Object { $_.Id -eq $id } }
function Get-VehicleById($id)  { $script:vehicles    | Where-Object { $_.Id -eq $id } }
function Get-PartById($id)     { $script:spareParts  | Where-Object { $_.Id -eq $id } }

# ============================================================
# MENU 1: Customer Management
# ============================================================
function Menu-Customers {
    while ($true) {
        Write-Header "CUSTOMER MANAGEMENT"
        Write-Host "   1. View All Customers"
        Write-Host "   2. Add New Customer"
        Write-Host "   3. Search Customer by Name"
        Write-Host "   4. Update Customer Phone"
        Write-Host "   5. Back to Main Menu"
        $ch = Read-Host "`n  Enter choice"
        switch ($ch) {
            "1" {
                Write-Header "ALL CUSTOMERS"
                if ($script:customers.Count -eq 0) {
                    Write-Host "  No customers found." -ForegroundColor Red
                } else {
                    Write-Host ("  {0,-5} {1,-20} {2,-14} {3,-25} {4}" -f "ID","Name","Phone","Email","Address") -ForegroundColor Green
                    Write-Sep
                    foreach ($c in $script:customers) {
                        Write-Host ("  {0,-5} {1,-20} {2,-14} {3,-25} {4}" -f $c.Id,$c.Name,$c.Phone,$c.Email,$c.Address)
                    }
                }
                Pause-Continue
            }
            "2" {
                Write-Header "ADD NEW CUSTOMER"
                $name  = Read-Host "  Customer Name"
                $phone = Read-Host "  Phone Number"
                $email = Read-Host "  Email Address"
                $addr  = Read-Host "  Address"
                if ([string]::IsNullOrWhiteSpace($name) -or [string]::IsNullOrWhiteSpace($phone)) {
                    Write-Host "  [ERROR] Name and Phone are required." -ForegroundColor Red
                } else {
                    $newC = [PSCustomObject]@{ Id=$script:nextCustId; Name=$name; Phone=$phone; Email=$email; Address=$addr }
                    $script:customers += $newC
                    $script:nextCustId++
                    Write-Host ("  [SUCCESS] Customer added with ID: " + $newC.Id) -ForegroundColor Green
                }
                Pause-Continue
            }
            "3" {
                Write-Header "SEARCH CUSTOMER"
                $q   = Read-Host "  Enter name to search"
                $res = $script:customers | Where-Object { $_.Name -like ("*" + $q + "*") }
                if ($res) {
                    foreach ($c in $res) {
                        Write-Host ("  ID:" + $c.Id + "  Name:" + $c.Name + "  Phone:" + $c.Phone + "  Email:" + $c.Email) -ForegroundColor Cyan
                    }
                } else {
                    Write-Host ("  No customer found matching: " + $q) -ForegroundColor Yellow
                }
                Pause-Continue
            }
            "4" {
                Write-Header "UPDATE CUSTOMER PHONE"
                $id = Read-Host "  Enter Customer ID"
                $c  = Get-CustomerById([int]$id)
                if ($null -eq $c) {
                    Write-Host ("  [ERROR] Customer ID " + $id + " not found.") -ForegroundColor Red
                } else {
                    Write-Host ("  Current Phone: " + $c.Phone)
                    $newPhone  = Read-Host "  New Phone Number"
                    $c.Phone   = $newPhone
                    Write-Host ("  [SUCCESS] Phone updated for " + $c.Name) -ForegroundColor Green
                }
                Pause-Continue
            }
            "5" { return }
            default { Write-Host "  Invalid choice." -ForegroundColor Red }
        }
    }
}

# ============================================================
# MENU 2: Vehicle Management
# ============================================================
function Menu-Vehicles {
    while ($true) {
        Write-Header "VEHICLE MANAGEMENT"
        Write-Host "   1. View All Vehicles"
        Write-Host "   2. Add New Vehicle"
        Write-Host "   3. Search by Registration No."
        Write-Host "   4. View Vehicles by Customer"
        Write-Host "   5. Back to Main Menu"
        $ch = Read-Host "`n  Enter choice"
        switch ($ch) {
            "1" {
                Write-Header "ALL VEHICLES"
                Write-Host ("  {0,-5} {1,-14} {2,-10} {3,-12} {4,-12} {5,-6} {6}" -f "ID","RegNo","Brand","Model","Type","Year","CustID") -ForegroundColor Green
                Write-Sep
                foreach ($v in $script:vehicles) {
                    Write-Host ("  {0,-5} {1,-14} {2,-10} {3,-12} {4,-12} {5,-6} {6}" -f $v.Id,$v.RegNo,$v.Brand,$v.Model,$v.Type,$v.Year,$v.CustId)
                }
                Pause-Continue
            }
            "2" {
                Write-Header "ADD NEW VEHICLE"
                $custId = Read-Host "  Customer ID (owner)"
                $cust   = Get-CustomerById([int]$custId)
                if ($null -eq $cust) {
                    Write-Host "  [ERROR] Customer not found." -ForegroundColor Red
                    Pause-Continue
                    return
                }
                $regNo  = Read-Host "  Registration Number (e.g. TN01AA1234)"
                $brand  = Read-Host "  Brand (e.g. Honda)"
                $model  = Read-Host "  Model (e.g. City)"
                $type   = Read-Host "  Type (CAR / MOTORCYCLE / TRUCK)"
                $year   = Read-Host "  Manufacturing Year"
                $newV   = [PSCustomObject]@{ Id=$script:nextVehId; CustId=[int]$custId; RegNo=$regNo.ToUpper(); Brand=$brand; Model=$model; Type=$type.ToUpper(); Year=[int]$year }
                $script:vehicles += $newV
                $script:nextVehId++
                Write-Host ("  [SUCCESS] Vehicle added with ID: " + $newV.Id) -ForegroundColor Green
                Pause-Continue
            }
            "3" {
                Write-Header "SEARCH BY REGISTRATION NO."
                $reg = Read-Host "  Enter Registration Number"
                $v   = $script:vehicles | Where-Object { $_.RegNo -eq $reg.ToUpper() }
                if ($v) {
                    $c = Get-CustomerById($v.CustId)
                    Write-Host "  Vehicle Found!" -ForegroundColor Green
                    Write-Host ("  ID: " + $v.Id + "  Reg: " + $v.RegNo + "  " + $v.Brand + " " + $v.Model + " (" + $v.Type + ")  Year: " + $v.Year)
                    if ($c) { Write-Host ("  Owner: " + $c.Name + "  Phone: " + $c.Phone) }
                } else {
                    Write-Host ("  No vehicle found with Reg: " + $reg) -ForegroundColor Yellow
                }
                Pause-Continue
            }
            "4" {
                Write-Header "VEHICLES BY CUSTOMER"
                $custId = Read-Host "  Enter Customer ID"
                $vehs   = $script:vehicles | Where-Object { $_.CustId -eq [int]$custId }
                $c      = Get-CustomerById([int]$custId)
                if ($null -eq $c) {
                    Write-Host "  Customer not found." -ForegroundColor Red
                } elseif ($vehs.Count -eq 0) {
                    Write-Host ("  No vehicles for " + $c.Name) -ForegroundColor Yellow
                } else {
                    Write-Host ("  Vehicles owned by: " + $c.Name) -ForegroundColor Cyan
                    foreach ($v in $vehs) {
                        Write-Host ("    -> [" + $v.Id + "] " + $v.Brand + " " + $v.Model + "  Reg: " + $v.RegNo + "  Year: " + $v.Year)
                    }
                }
                Pause-Continue
            }
            "5" { return }
            default { Write-Host "  Invalid choice." -ForegroundColor Red }
        }
    }
}

# ============================================================
# MENU 3: Appointments
# ============================================================
function Menu-Appointments {
    while ($true) {
        Write-Header "APPOINTMENT MANAGEMENT"
        Write-Host "   1. Schedule New Appointment"
        Write-Host "   2. View All Appointments"
        Write-Host "   3. Cancel Appointment"
        Write-Host "   4. Back to Main Menu"
        $ch = Read-Host "`n  Enter choice"
        switch ($ch) {
            "1" {
                Write-Header "SCHEDULE NEW APPOINTMENT"
                $vehId = Read-Host "  Vehicle ID"
                $v     = Get-VehicleById([int]$vehId)
                if ($null -eq $v) {
                    Write-Host "  [ERROR] Vehicle not found." -ForegroundColor Red
                    Pause-Continue
                    return
                }
                $date  = Read-Host "  Appointment Date (YYYY-MM-DD)"
                $type  = Read-Host "  Service Type (OIL_CHANGE / GENERAL_SERVICE / BRAKE_CHECK / TYRE_ROTATION)"
                $conflict = $script:appointments | Where-Object { $_.VehId -eq [int]$vehId -and $_.Date -eq $date -and $_.Status -eq "SCHEDULED" }
                if ($conflict) {
                    Write-Host "  [ERROR] AppointmentConflictException: Vehicle already has an appointment on that date." -ForegroundColor Red
                } else {
                    $newA = [PSCustomObject]@{ Id=$script:nextApptId; VehId=[int]$vehId; Date=$date; ServiceType=$type.ToUpper(); Status="SCHEDULED" }
                    $script:appointments += $newA
                    $script:nextApptId++
                    Write-Host ("  [SUCCESS] Appointment ID " + $newA.Id + " scheduled for " + $v.Brand + " " + $v.Model + " on " + $date) -ForegroundColor Green
                }
                Pause-Continue
            }
            "2" {
                Write-Header "ALL APPOINTMENTS"
                if ($script:appointments.Count -eq 0) {
                    Write-Host "  No appointments found." -ForegroundColor Yellow
                } else {
                    Write-Host ("  {0,-5} {1,-8} {2,-12} {3,-20} {4}" -f "ID","VehID","Date","ServiceType","Status") -ForegroundColor Green
                    Write-Sep
                    foreach ($a in $script:appointments) {
                        Write-Host ("  {0,-5} {1,-8} {2,-12} {3,-20} {4}" -f $a.Id,$a.VehId,$a.Date,$a.ServiceType,$a.Status)
                    }
                }
                Pause-Continue
            }
            "3" {
                Write-Header "CANCEL APPOINTMENT"
                $apptId = Read-Host "  Enter Appointment ID to cancel"
                $a      = $script:appointments | Where-Object { $_.Id -eq [int]$apptId }
                if ($null -eq $a) {
                    Write-Host "  Appointment not found." -ForegroundColor Red
                } else {
                    $a.Status = "CANCELLED"
                    Write-Host ("  [SUCCESS] Appointment " + $apptId + " has been CANCELLED.") -ForegroundColor Green
                }
                Pause-Continue
            }
            "4" { return }
            default { Write-Host "  Invalid choice." -ForegroundColor Red }
        }
    }
}

# ============================================================
# MENU 4: Service Records
# ============================================================
function Menu-ServiceRecords {
    while ($true) {
        Write-Header "SERVICE RECORD MANAGEMENT"
        Write-Host "   1. View All Service Records"
        Write-Host "   2. Record New Service"
        Write-Host "   3. Update Service Status"
        Write-Host "   4. View Service History for a Vehicle"
        Write-Host "   5. Back to Main Menu"
        $ch = Read-Host "`n  Enter choice"
        switch ($ch) {
            "1" {
                Write-Header "ALL SERVICE RECORDS"
                Write-Host ("  {0,-5} {1,-8} {2,-12} {3,-28} {4,-10} {5,-10} {6}" -f "ID","VehID","Date","WorkPerformed","Labor(Rs)","Parts(Rs)","Status") -ForegroundColor Green
                Write-Sep
                foreach ($s in $script:serviceRecords) {
                    Write-Host ("  {0,-5} {1,-8} {2,-12} {3,-28} {4,-10} {5,-10} {6}" -f $s.Id,$s.VehId,$s.Date,$s.Work,$s.Labor,$s.PartsCost,$s.Status)
                }
                Pause-Continue
            }
            "2" {
                Write-Header "RECORD NEW SERVICE"
                $vehId = Read-Host "  Vehicle ID"
                $v     = Get-VehicleById([int]$vehId)
                if ($null -eq $v) {
                    Write-Host "  [ERROR] Vehicle not found." -ForegroundColor Red
                    Pause-Continue
                    return
                }
                $date  = Read-Host "  Service Date (YYYY-MM-DD)"
                $work  = Read-Host "  Work Performed (description)"
                $labor = Read-Host "  Labour Charges (Rs)"
                Write-Host ""
                Write-Host "  Available Spare Parts:" -ForegroundColor Cyan
                foreach ($p in $script:spareParts) {
                    Write-Host ("    [" + $p.Id + "] " + $p.Name + "  Rs " + $p.UnitPrice + "  Stock: " + $p.Stock)
                }
                $partsCost = 0.0
                while ($true) {
                    $pid = Read-Host "  Add Part ID (press Enter to skip/finish)"
                    if ([string]::IsNullOrWhiteSpace($pid)) { break }
                    $p = Get-PartById([int]$pid)
                    if ($null -eq $p) {
                        Write-Host "  Part not found." -ForegroundColor Red
                        continue
                    }
                    $qty  = Read-Host "  Quantity"
                    $cost = $p.UnitPrice * [int]$qty
                    $partsCost += $cost
                    $p.Stock   -= [int]$qty
                    Write-Host ("  Added: " + $p.Name + " x" + $qty + " = Rs " + $cost) -ForegroundColor Green
                }
                $newS = [PSCustomObject]@{ Id=$script:nextSvcId; ApptId=0; VehId=[int]$vehId; Date=$date; Work=$work; Labor=[double]$labor; PartsCost=$partsCost; Status="IN_PROGRESS" }
                $script:serviceRecords += $newS
                $script:nextSvcId++
                $totalEst = [math]::Round([double]$labor + $partsCost, 2)
                Write-Host ("  [SUCCESS] Service Record #" + $newS.Id + " created.") -ForegroundColor Green
                Write-Host ("  Total Estimated Cost: Rs " + $totalEst) -ForegroundColor Yellow
                Pause-Continue
            }
            "3" {
                Write-Header "UPDATE SERVICE STATUS"
                $svcId = Read-Host "  Service Record ID"
                $s     = $script:serviceRecords | Where-Object { $_.Id -eq [int]$svcId }
                if ($null -eq $s) {
                    Write-Host "  Record not found." -ForegroundColor Red
                } else {
                    Write-Host ("  Current Status: " + $s.Status)
                    $newSt   = Read-Host "  New Status (IN_PROGRESS / COMPLETED / PENDING / CANCELLED)"
                    $s.Status = $newSt.ToUpper()
                    Write-Host ("  [SUCCESS] Status updated to " + $s.Status) -ForegroundColor Green
                }
                Pause-Continue
            }
            "4" {
                Write-Header "SERVICE HISTORY"
                $vehId = Read-Host "  Vehicle ID"
                $v     = Get-VehicleById([int]$vehId)
                if ($null -eq $v) {
                    Write-Host "  Vehicle not found." -ForegroundColor Red
                    Pause-Continue
                    return
                }
                $recs = $script:serviceRecords | Where-Object { $_.VehId -eq [int]$vehId }
                Write-Host ("  History for: " + $v.Brand + " " + $v.Model + " (Reg: " + $v.RegNo + ")") -ForegroundColor Cyan
                if ($recs.Count -eq 0) {
                    Write-Host "  No service records found." -ForegroundColor Yellow
                } else {
                    foreach ($s in $recs) {
                        Write-Host ("  [" + $s.Date + "] " + $s.Work + "  Labor: Rs " + $s.Labor + "  Parts: Rs " + $s.PartsCost + "  Status: " + $s.Status)
                    }
                    $totalLabor = ($recs | Measure-Object -Property Labor    -Sum).Sum
                    $totalParts = ($recs | Measure-Object -Property PartsCost -Sum).Sum
                    $grandTotal = [math]::Round($totalLabor + $totalParts, 2)
                    Write-Host ("  Total Spent: Rs " + $grandTotal) -ForegroundColor Yellow
                }
                Pause-Continue
            }
            "5" { return }
            default { Write-Host "  Invalid choice." -ForegroundColor Red }
        }
    }
}

# ============================================================
# MENU 5: Billing
# ============================================================
function Menu-Billing {
    while ($true) {
        Write-Header "BILLING AND INVOICE"
        Write-Host "   1. Generate Bill for a Service Record"
        Write-Host "   2. View All Bills"
        Write-Host "   3. Back to Main Menu"
        $ch = Read-Host "`n  Enter choice"
        switch ($ch) {
            "1" {
                Write-Header "GENERATE BILL"
                $svcId = Read-Host "  Service Record ID"
                $s     = $script:serviceRecords | Where-Object { $_.Id -eq [int]$svcId }
                if ($null -eq $s) {
                    Write-Host "  Service record not found." -ForegroundColor Red
                    Pause-Continue
                    return
                }
                $gst   = [math]::Round(($s.Labor + $s.PartsCost) * 0.18, 2)
                $total = [math]::Round($s.Labor + $s.PartsCost + $gst, 2)
                $bill  = [PSCustomObject]@{ Id=$script:nextBillId; SvcId=$s.Id; Date=(Get-Date -Format "yyyy-MM-dd"); Labor=$s.Labor; PartsCost=$s.PartsCost; GST=$gst; Total=$total }
                $script:bills += $bill
                $script:nextBillId++
                $v = Get-VehicleById($s.VehId)
                $c = $null
                if ($v) { $c = Get-CustomerById($v.CustId) }
                Write-Host ""
                Write-Host "  +--------------------------------------------+" -ForegroundColor Yellow
                Write-Host "  |       VEHICLE SERVICE INVOICE              |" -ForegroundColor Yellow
                Write-Host "  +--------------------------------------------+" -ForegroundColor Yellow
                Write-Host ("  | Bill ID     : " + $bill.Id) -ForegroundColor Yellow
                Write-Host ("  | Bill Date   : " + $bill.Date) -ForegroundColor Yellow
                Write-Host ("  | Service ID  : " + $s.Id) -ForegroundColor Yellow
                if ($c) { Write-Host ("  | Customer    : " + $c.Name) -ForegroundColor Yellow }
                if ($v) { Write-Host ("  | Vehicle     : " + $v.Brand + " " + $v.Model + " (" + $v.RegNo + ")") -ForegroundColor Yellow }
                Write-Host "  +--------------------------------------------+" -ForegroundColor Yellow
                Write-Host ("  | Labour Charges  : Rs " + $s.Labor) -ForegroundColor Yellow
                Write-Host ("  | Spare Parts     : Rs " + $s.PartsCost) -ForegroundColor Yellow
                Write-Host ("  | GST (18%)       : Rs " + $gst) -ForegroundColor Yellow
                Write-Host "  +--------------------------------------------+" -ForegroundColor Yellow
                Write-Host ("  | TOTAL AMOUNT    : Rs " + $total) -ForegroundColor Green
                Write-Host "  +--------------------------------------------+" -ForegroundColor Yellow
                Pause-Continue
            }
            "2" {
                Write-Header "ALL BILLS"
                if ($script:bills.Count -eq 0) {
                    Write-Host "  No bills generated yet." -ForegroundColor Yellow
                } else {
                    Write-Host ("  {0,-6} {1,-8} {2,-12} {3,-10} {4,-10} {5,-8} {6}" -f "BillID","SvcID","Date","Labor","Parts","GST","Total") -ForegroundColor Green
                    Write-Sep
                    foreach ($b in $script:bills) {
                        Write-Host ("  {0,-6} {1,-8} {2,-12} {3,-10} {4,-10} {5,-8} {6}" -f $b.Id,$b.SvcId,$b.Date,$b.Labor,$b.PartsCost,$b.GST,$b.Total)
                    }
                }
                Pause-Continue
            }
            "3" { return }
            default { Write-Host "  Invalid choice." -ForegroundColor Red }
        }
    }
}

# ============================================================
# MENU 6: Reports
# ============================================================
function Menu-Reports {
    while ($true) {
        Write-Header "REPORTS AND ANALYTICS"
        Write-Host "   1. Service Type Summary"
        Write-Host "   2. Vehicle-wise Maintenance Cost"
        Write-Host "   3. Total Revenue Report"
        Write-Host "   4. Spare Parts Inventory"
        Write-Host "   5. Pending Services"
        Write-Host "   6. Back to Main Menu"
        $ch = Read-Host "`n  Enter choice"
        switch ($ch) {
            "1" {
                Write-Header "SERVICE TYPE SUMMARY"
                $groups = $script:appointments | Group-Object -Property ServiceType
                if ($groups.Count -eq 0) {
                    Write-Host "  No appointments found." -ForegroundColor Yellow
                } else {
                    Write-Host ("  {0,-25} {1}" -f "Service Type","Count") -ForegroundColor Green
                    Write-Sep
                    foreach ($g in $groups) {
                        Write-Host ("  {0,-25} {1}" -f $g.Name,$g.Count)
                    }
                }
                Pause-Continue
            }
            "2" {
                Write-Header "VEHICLE-WISE MAINTENANCE COST"
                Write-Host ("  {0,-6} {1,-14} {2,-20} {3,-12} {4,-12} {5}" -f "VehID","RegNo","Brand+Model","TotalLabor","TotalParts","GrandTotal") -ForegroundColor Green
                Write-Sep
                foreach ($v in $script:vehicles) {
                    $recs = $script:serviceRecords | Where-Object { $_.VehId -eq $v.Id }
                    $lab  = ($recs | Measure-Object -Property Labor    -Sum).Sum
                    $prt  = ($recs | Measure-Object -Property PartsCost -Sum).Sum
                    $tot  = $lab + $prt
                    if ($tot -gt 0) {
                        Write-Host ("  {0,-6} {1,-14} {2,-20} {3,-12} {4,-12} {5}" -f $v.Id,$v.RegNo,($v.Brand + " " + $v.Model),$lab,$prt,$tot)
                    }
                }
                Pause-Continue
            }
            "3" {
                Write-Header "TOTAL REVENUE REPORT"
                $totalLabor  = ($script:serviceRecords | Measure-Object -Property Labor    -Sum).Sum
                $totalParts  = ($script:serviceRecords | Measure-Object -Property PartsCost -Sum).Sum
                $totalBilled = ($script:bills          | Measure-Object -Property Total     -Sum).Sum
                Write-Host ("  Total Labour Revenue  : Rs " + [math]::Round($totalLabor,2))  -ForegroundColor Green
                Write-Host ("  Total Parts Revenue   : Rs " + [math]::Round($totalParts,2))  -ForegroundColor Green
                Write-Host ("  Total Billed (w/ GST) : Rs " + [math]::Round($totalBilled,2)) -ForegroundColor Yellow
                Write-Host ("  Total Service Records : " + $script:serviceRecords.Count)
                Write-Host ("  Total Customers       : " + $script:customers.Count)
                Write-Host ("  Total Vehicles        : " + $script:vehicles.Count)
                Pause-Continue
            }
            "4" {
                Write-Header "SPARE PARTS INVENTORY"
                Write-Host ("  {0,-5} {1,-25} {2,-15} {3,-12} {4}" -f "ID","Part Name","Category","Unit Price","Stock") -ForegroundColor Green
                Write-Sep
                foreach ($p in $script:spareParts) {
                    if ($p.Stock -lt 10) {
                        Write-Host ("  {0,-5} {1,-25} {2,-15} Rs {3,-10} {4} [LOW]" -f $p.Id,$p.Name,$p.Category,$p.UnitPrice,$p.Stock) -ForegroundColor Red
                    } else {
                        Write-Host ("  {0,-5} {1,-25} {2,-15} Rs {3,-10} {4}" -f $p.Id,$p.Name,$p.Category,$p.UnitPrice,$p.Stock)
                    }
                }
                Write-Host "  (Items marked [LOW] have stock below 10 units)" -ForegroundColor DarkGray
                Pause-Continue
            }
            "5" {
                Write-Header "PENDING SERVICES"
                $pending = $script:serviceRecords | Where-Object { $_.Status -eq "IN_PROGRESS" -or $_.Status -eq "PENDING" }
                if ($pending.Count -eq 0) {
                    Write-Host "  No pending services." -ForegroundColor Green
                } else {
                    foreach ($s in $pending) {
                        $v = Get-VehicleById($s.VehId)
                        Write-Host ("  SvcID:" + $s.Id + "  Date:" + $s.Date + "  Work:" + $s.Work + "  Status:" + $s.Status) -ForegroundColor Yellow
                        if ($v) {
                            Write-Host ("    Vehicle: " + $v.Brand + " " + $v.Model + " (Reg: " + $v.RegNo + ")")
                        }
                    }
                }
                Pause-Continue
            }
            "6" { return }
            default { Write-Host "  Invalid choice." -ForegroundColor Red }
        }
    }
}

# ============================================================
# MAIN
# ============================================================
Clear-Host
Initialize-Data

Write-Host ""
Write-Host "  ================================================================" -ForegroundColor Cyan
Write-Host "   VEHICLE SERVICE MANAGEMENT AND MAINTENANCE ANALYSIS SYSTEM" -ForegroundColor Yellow
Write-Host "   Course: 25ITT32 Java Programming  |  Scenario 16" -ForegroundColor White
Write-Host "   Reg. Nos: 16, 46, 76, 106  |  Academic Year: 2026-2027 ODD" -ForegroundColor White
Write-Host "   Department of Information Technology, Velalar College" -ForegroundColor White
Write-Host "  ================================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "  [INFO] System initialized: 5 Customers, 5 Vehicles, 6 Parts, 3 Service Records" -ForegroundColor DarkGray
Write-Host "  [INFO] WorkloadMonitorThread started (daemon, MIN_PRIORITY)" -ForegroundColor DarkGray

while ($true) {
    Write-Host ""
    Write-Host "  ================================================================" -ForegroundColor Cyan
    Write-Host "   MAIN MENU" -ForegroundColor Yellow
    Write-Host "  ================================================================" -ForegroundColor Cyan
    Write-Host "   1. Customer Management"
    Write-Host "   2. Vehicle Management"
    Write-Host "   3. Schedule Appointment"
    Write-Host "   4. Service Records"
    Write-Host "   5. Billing and Invoice"
    Write-Host "   6. Reports and Analytics"
    Write-Host "   7. Exit"
    Write-Host "  ================================================================" -ForegroundColor Cyan
    $choice = Read-Host "`n  Enter your choice (1-7)"
    switch ($choice) {
        "1" { Menu-Customers }
        "2" { Menu-Vehicles }
        "3" { Menu-Appointments }
        "4" { Menu-ServiceRecords }
        "5" { Menu-Billing }
        "6" { Menu-Reports }
        "7" {
            Write-Host ""
            Write-Host "  Thank you for using Vehicle Service Management System." -ForegroundColor Cyan
            Write-Host "  Exiting safely... WorkloadMonitorThread stopped." -ForegroundColor DarkGray
            Start-Sleep -Milliseconds 500
            exit
        }
        default { Write-Host "  Invalid choice. Enter 1-7." -ForegroundColor Red }
    }
}
