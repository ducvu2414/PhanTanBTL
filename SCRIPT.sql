USE [master]
GO
/****** Object:  Database [luongsp]    Script Date: 18/04/2024 8:57:22 CH ******/
CREATE DATABASE [luongsp]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'luongsp', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\luongsp.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'luongsp_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\luongsp_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [luongsp] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [luongsp].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [luongsp] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [luongsp] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [luongsp] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [luongsp] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [luongsp] SET ARITHABORT OFF 
GO
ALTER DATABASE [luongsp] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [luongsp] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [luongsp] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [luongsp] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [luongsp] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [luongsp] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [luongsp] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [luongsp] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [luongsp] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [luongsp] SET  DISABLE_BROKER 
GO
ALTER DATABASE [luongsp] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [luongsp] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [luongsp] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [luongsp] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [luongsp] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [luongsp] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [luongsp] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [luongsp] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [luongsp] SET  MULTI_USER 
GO
ALTER DATABASE [luongsp] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [luongsp] SET DB_CHAINING OFF 
GO
ALTER DATABASE [luongsp] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [luongsp] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [luongsp] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [luongsp] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [luongsp] SET QUERY_STORE = ON
GO
ALTER DATABASE [luongsp] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [luongsp]
GO
/****** Object:  UserDefinedFunction [dbo].[tinhHeSoLuong]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[tinhHeSoLuong] (@ngayVaoLam DATE, @trinhDoVanHoa NVARCHAR(255))
RETURNS FLOAT
AS
BEGIN
    DECLARE @soNamLamViec INT;
    DECLARE @soBacTangLuong INT;
    DECLARE @heSoLuong FLOAT;
    DECLARE @heSoTienTrienCaoDang FLOAT = 0.31;
    DECLARE @heSoTienTrienDaiHoc FLOAT = 0.20;

    SET @soNamLamViec = DATEDIFF(YEAR, @ngayVaoLam, GETDATE());
    SET @soBacTangLuong = @soNamLamViec / 3;

    IF @trinhDoVanHoa= N'Cao đẳng'
    BEGIN
        SET @heSoLuong = 2.1 + @soBacTangLuong * @heSoTienTrienCaoDang;
    END
    ELSE IF @trinhDoVanHoa = N'Đại học'
    BEGIN
        SET @heSoLuong = 2.41 + @soBacTangLuong * @heSoTienTrienDaiHoc;
    END
    ELSE
    BEGIN
        SET @heSoLuong = 0;
    END

    RETURN @heSoLuong;
END;
GO
/****** Object:  UserDefinedFunction [dbo].[tinhHeSoLuongTLD]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create FUNCTION [dbo].[tinhHeSoLuongTLD](@tayNghe VARCHAR(255)) RETURNS FLOAT
AS
BEGIN
    DECLARE @trinhDo NVARCHAR(255) = @tayNghe;
    DECLARE @trinhDoOptions TABLE (trinhDo NVARCHAR(255), heSo FLOAT);
    INSERT INTO @trinhDoOptions VALUES ('Bậc 1', 1.0), ('Bậc 2', 1.2), ('Bậc 3', 1.4), ('Bậc 4', 1.6), ('Bậc 5', 1.8);

    DECLARE @heSoLuong FLOAT = COALESCE((SELECT heSo FROM @trinhDoOptions WHERE trinhDo = @trinhDo), 1.0);

    RETURN @heSoLuong;
END;
GO
/****** Object:  UserDefinedFunction [dbo].[tinhPhuCapThamNien]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[tinhPhuCapThamNien](@ngayVaoLam DATE, @heSo FLOAT) RETURNS FLOAT
AS
BEGIN
    DECLARE @ngayVaoLamLocal DATE = @ngayVaoLam;
    DECLARE @ngayHienTai DATE = GETDATE();
    DECLARE @soNamLamViec INT = DATEDIFF(YEAR, @ngayVaoLamLocal, @ngayHienTai);
    DECLARE @tyLePhuCap FLOAT;

    IF @soNamLamViec >= 5 AND @soNamLamViec < 10
        SET @tyLePhuCap = 5.0;
    ELSE IF @soNamLamViec >= 10 AND @soNamLamViec < 15
        SET @tyLePhuCap = 10.0;
    ELSE IF @soNamLamViec >= 15 AND @soNamLamViec < 20
        SET @tyLePhuCap = 15.0;
    ELSE IF @soNamLamViec >= 20
        SET @tyLePhuCap = 20.0;
    ELSE
        SET @tyLePhuCap = 0.0;

    DECLARE @phuCapThamNien FLOAT = 3000000 * @heSo * (@tyLePhuCap / 100);

    RETURN @phuCapThamNien;
END;
GO
/****** Object:  UserDefinedFunction [dbo].[tinhPhuCapThamNienNhanVien]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[tinhPhuCapThamNienNhanVien](@luongCoBan FLOAT, @ngayVaoLam DATE, @heSo FLOAT) RETURNS FLOAT
AS
BEGIN
    DECLARE @ngayVaoLamLocal DATE = @ngayVaoLam;
    DECLARE @ngayHienTai DATE = GETDATE();
    DECLARE @soNamLamViec INT = DATEDIFF(YEAR, @ngayVaoLamLocal, @ngayHienTai);
    DECLARE @tyLePhuCap FLOAT;

    IF @soNamLamViec >= 5 AND @soNamLamViec < 10
        SET @tyLePhuCap = 5.0;
    ELSE IF @soNamLamViec >= 10 AND @soNamLamViec < 15
        SET @tyLePhuCap = 10.0;
    ELSE IF @soNamLamViec >= 15 AND @soNamLamViec < 20
        SET @tyLePhuCap = 15.0;
    ELSE IF @soNamLamViec >= 20
        SET @tyLePhuCap = 20.0;
    ELSE
        SET @tyLePhuCap = 0.0;

    DECLARE @phuCapThamNien FLOAT = @luongCoBan * @heSo * (@tyLePhuCap / 100);

    RETURN @phuCapThamNien;
END;
GO
/****** Object:  Table [dbo].[BangChamCongNhanVien]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BangChamCongNhanVien](
	[maBCCNhanVien] [varchar](255) NOT NULL,
	[ngayChamCong] [date] NULL,
	[soGioTangCa] [int] NOT NULL,
	[trangThaiDiLam] [varchar](255) NULL,
	[maBangLuong] [varchar](255) NULL,
	[maCongNhanVien] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maBCCNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BangChamCongThoLamDan]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BangChamCongThoLamDan](
	[maBCCThoLamDan] [varchar](255) NOT NULL,
	[ngayChamCong] [date] NULL,
	[soLuongSanPham] [int] NOT NULL,
	[maBangLuong] [varchar](255) NULL,
	[maCongDoan] [varchar](255) NULL,
	[ngayPhanCong] [date] NULL,
	[maCongNhanVien] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maBCCThoLamDan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BangLuongNhanVien]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BangLuongNhanVien](
	[maBangLuong] [varchar](255) NOT NULL,
	[nam] [int] NOT NULL,
	[soGioTangCaChuNhat] [int] NOT NULL,
	[soGioTangCaNgayThuong] [int] NOT NULL,
	[soNgayLamChuNhat] [real] NOT NULL,
	[soNgayNghiCoPhep] [int] NOT NULL,
	[soNgayNghiKhongPhep] [int] NOT NULL,
	[soNgayThuongDiLam] [real] NOT NULL,
	[thang] [int] NOT NULL,
	[maCongNhanVien] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maBangLuong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BangLuongThoLamDan]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BangLuongThoLamDan](
	[maBangLuong] [varchar](255) NOT NULL,
	[nam] [int] NOT NULL,
	[soLuongSanPham] [int] NOT NULL,
	[thang] [int] NOT NULL,
	[maThoLamDan] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maBangLuong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BangPhanCong]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BangPhanCong](
	[ngayPhanCong] [date] NOT NULL,
	[soLuongSanPham] [int] NOT NULL,
	[maCongDoan] [varchar](255) NOT NULL,
	[maCongNhanVien] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maCongDoan] ASC,
	[ngayPhanCong] ASC,
	[maCongNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CongDoan]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CongDoan](
	[maCongDoan] [varchar](255) NOT NULL,
	[giaCongDoan] [real] NOT NULL,
	[tenCongDoan] [nvarchar](255) NULL,
	[maSanPham] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maCongDoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CongNhanVien]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CongNhanVien](
	[maCongNhanVien] [varchar](255) NOT NULL,
	[diaChi] [nvarchar](255) NULL,
	[gioiTinh] [varchar](255) NULL,
	[hoTen] [nvarchar](255) NULL,
	[maCanCuocCongDan] [varchar](255) NULL,
	[ngaySinh] [date] NULL,
	[ngayVaoLam] [date] NULL,
	[soDienThoai] [varchar](255) NULL,
	[trangThai] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maCongNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Dan]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Dan](
	[maSanPham] [varchar](255) NOT NULL,
	[can] [nvarchar](255) NULL,
	[cauNgua] [nvarchar](255) NULL,
	[day] [nvarchar](255) NULL,
	[eoLung] [nvarchar](255) NULL,
	[giaBan] [float] NOT NULL,
	[khoa] [nvarchar](255) NULL,
	[loaiSanPham] [nvarchar](255) NULL,
	[matDan] [nvarchar](255) NULL,
	[matPhim] [nvarchar](255) NULL,
	[moTa] [nvarchar](255) NULL,
	[tenSanPham] [nvarchar](255) NULL,
	[trangThai] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maSanPham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[chucVu] [varchar](255) NULL,
	[luongCoBan] [float] NOT NULL,
	[trinhDoVanHoa] [nvarchar](255) NULL,
	[maCongNhanVien] [varchar](255) NOT NULL,
	[maPhongBan] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maCongNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhongBan]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhongBan](
	[maPhongBan] [varchar](255) NOT NULL,
	[tenPhongBan] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maPhongBan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[taiKhoan] [varchar](255) NOT NULL,
	[matKhau] [varchar](255) NULL,
	[maCongNhanVien] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[taiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ThoLamDan]    Script Date: 18/04/2024 8:57:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ThoLamDan](
	[tayNghe] [varchar](255) NULL,
	[maCongNhanVien] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maCongNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[BangChamCongNhanVien] ([maBCCNhanVien], [ngayChamCong], [soGioTangCa], [trangThaiDiLam], [maBangLuong], [maCongNhanVien]) VALUES (N'BCCNV00120240415', CAST(N'2024-04-15' AS Date), 0, N'Làm nguyên ngày công', N'BLNV001042024', N'NV001')
GO
INSERT [dbo].[BangChamCongThoLamDan] ([maBCCThoLamDan], [ngayChamCong], [soLuongSanPham], [maBangLuong], [maCongDoan], [ngayPhanCong], [maCongNhanVien]) VALUES (N'BCCTLDD00120240416', CAST(N'2024-04-16' AS Date), 56, N'BLTLDD001042024', N'SP001CD001', CAST(N'2024-04-16' AS Date), N'TLD001')
INSERT [dbo].[BangChamCongThoLamDan] ([maBCCThoLamDan], [ngayChamCong], [soLuongSanPham], [maBangLuong], [maCongDoan], [ngayPhanCong], [maCongNhanVien]) VALUES (N'BCCTLDD00120240417', CAST(N'2024-04-17' AS Date), 69, N'BLTLDD001042024', N'SP001CD002', CAST(N'2024-04-17' AS Date), N'TLD001')
INSERT [dbo].[BangChamCongThoLamDan] ([maBCCThoLamDan], [ngayChamCong], [soLuongSanPham], [maBangLuong], [maCongDoan], [ngayPhanCong], [maCongNhanVien]) VALUES (N'BCCTLDD00220240416', CAST(N'2024-04-16' AS Date), 67, N'BLTLDD002042024', N'SP001CD005', CAST(N'2024-04-16' AS Date), N'TLD002')
INSERT [dbo].[BangChamCongThoLamDan] ([maBCCThoLamDan], [ngayChamCong], [soLuongSanPham], [maBangLuong], [maCongDoan], [ngayPhanCong], [maCongNhanVien]) VALUES (N'BCCTLDD00320240416', CAST(N'2024-04-16' AS Date), 67, N'BLTLDD003042024', N'SP002CD001', CAST(N'2024-04-16' AS Date), N'TLD003')
INSERT [dbo].[BangChamCongThoLamDan] ([maBCCThoLamDan], [ngayChamCong], [soLuongSanPham], [maBangLuong], [maCongDoan], [ngayPhanCong], [maCongNhanVien]) VALUES (N'BCCTLDD00420240416', CAST(N'2024-04-16' AS Date), 76, N'BLTLDD004042024', N'SP002CD002', CAST(N'2024-04-16' AS Date), N'TLD004')
GO
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV001042024', 2024, 0, 0, 0, 0, 0, 1, 4, N'NV001')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV002042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV002')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV003042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV003')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV004042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV004')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV005042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV005')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV006042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV006')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV007042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV007')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV008042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV008')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV009042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV009')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV010042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV010')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV011042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV011')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV012042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV012')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV013042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV013')
INSERT [dbo].[BangLuongNhanVien] ([maBangLuong], [nam], [soGioTangCaChuNhat], [soGioTangCaNgayThuong], [soNgayLamChuNhat], [soNgayNghiCoPhep], [soNgayNghiKhongPhep], [soNgayThuongDiLam], [thang], [maCongNhanVien]) VALUES (N'BLNV014042024', 2024, 0, 0, 0, 0, 0, 0, 4, N'NV014')
GO
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD001042024', 2024, 125, 4, N'TLD001')
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD002042024', 2024, 67, 4, N'TLD002')
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD003042024', 2024, 67, 4, N'TLD003')
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD004042024', 2024, 76, 4, N'TLD004')
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD005042024', 2024, 0, 4, N'TLD005')
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD006042024', 2024, 0, 4, N'TLD006')
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD008042024', 2024, 0, 4, N'TLD008')
INSERT [dbo].[BangLuongThoLamDan] ([maBangLuong], [nam], [soLuongSanPham], [thang], [maThoLamDan]) VALUES (N'BLTLDD009042024', 2024, 0, 4, N'TLD009')
GO
INSERT [dbo].[BangPhanCong] ([ngayPhanCong], [soLuongSanPham], [maCongDoan], [maCongNhanVien]) VALUES (CAST(N'2024-04-16' AS Date), 56, N'SP001CD001', N'TLD001')
INSERT [dbo].[BangPhanCong] ([ngayPhanCong], [soLuongSanPham], [maCongDoan], [maCongNhanVien]) VALUES (CAST(N'2024-04-17' AS Date), 69, N'SP001CD002', N'TLD001')
INSERT [dbo].[BangPhanCong] ([ngayPhanCong], [soLuongSanPham], [maCongDoan], [maCongNhanVien]) VALUES (CAST(N'2024-04-16' AS Date), 67, N'SP001CD005', N'TLD002')
INSERT [dbo].[BangPhanCong] ([ngayPhanCong], [soLuongSanPham], [maCongDoan], [maCongNhanVien]) VALUES (CAST(N'2024-04-16' AS Date), 67, N'SP002CD001', N'TLD003')
INSERT [dbo].[BangPhanCong] ([ngayPhanCong], [soLuongSanPham], [maCongDoan], [maCongNhanVien]) VALUES (CAST(N'2024-04-16' AS Date), 76, N'SP002CD002', N'TLD004')
GO
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP001CD001', 10000, N'Lựa chọn chất liệu gỗ', N'SP001')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP001CD002', 1000, N'Chế tác mặt đàn', N'SP001')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP001CD003', 1200, N'Xử lý độ ẩm', N'SP001')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP001CD004', 123, N'Chế tác cầu ngựa', N'SP001')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP001CD005', 123, N'Tinh chỉnh âm thanh', N'SP001')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP002CD001', 5000, N'Chế tác mặt đàn', N'SP002')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP002CD002', 111, N'Xử lý độ ẩm', N'SP002')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP004CD001', 65000, N'Chế tác cần đàn', N'SP004')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP004CD002', 123, N'Lựa chọn chất liệu gỗ', N'SP004')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP004CD003', 113000, N'Chế tác eo lưng', N'SP004')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP004CD004', 12344, N'Chế tác mặt phím', N'SP004')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP004CD005', 12, N'Chế tác cầu ngựa', N'SP004')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP004CD006', 122, N'Xử lý độ ẩm', N'SP004')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP004CD007', 3333, N'Hoàn thiện và làm đẹp', N'SP004')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP005CD001', 2133, N'Lựa chọn chất liệu gỗ', N'SP005')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP005CD002', 1220, N'Chế tác mặt đàn', N'SP005')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP005CD003', 12233, N'Xử lý độ ẩm', N'SP005')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP005CD004', 123, N'Thiết kế lỗ thoát âm', N'SP005')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP005CD005', 123, N'Tinh chỉnh âm thanh', N'SP005')
INSERT [dbo].[CongDoan] ([maCongDoan], [giaCongDoan], [tenCongDoan], [maSanPham]) VALUES (N'SP005CD006', 13, N'Chế tác cầu ngựa', N'SP005')
GO
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV001', N'Quận Tân Bình, Tp. Hồ Chí Minh', N'Nam', N'Chung Gia b', N'056203000071', CAST(N'2003-09-24' AS Date), CAST(N'2022-03-10' AS Date), N'0987654321', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV002', N'Quận 1, Tp. Hồ Chí Minh', N'Nam', N'Nguyễn Văn A', N'012345678912', CAST(N'1995-05-15' AS Date), CAST(N'2020-06-20' AS Date), N'0987654322', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV003', N'Quận 2, Tp. Hồ Chí Minh', N'Nu', N'Trần Thị B', N'098765431221', CAST(N'1990-12-10' AS Date), CAST(N'2018-04-15' AS Date), N'0123456789', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV004', N'Quận 3, Tp. Hồ Chí Minh', N'Nam', N'Lê Văn C', N'012332456789', CAST(N'1987-07-05' AS Date), CAST(N'2015-10-10' AS Date), N'0987654323', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV005', N'Quận 4, Tp. Hồ Chí Minh', N'Nu', N'Phạm Thị D', N'098547654321', CAST(N'1988-09-20' AS Date), CAST(N'2017-12-05' AS Date), N'0123456789', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV006', N'Quận 5, Tp. Hồ Chí Minh', N'Nam', N'Huỳnh Văn E', N'012345646789', CAST(N'1993-02-12' AS Date), CAST(N'2016-08-30' AS Date), N'0987654324', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV007', N'Quận 6, Tp. Hồ Chí Minh', N'Nu', N'Võ Thị F', N'098765234321', CAST(N'1986-11-25' AS Date), CAST(N'2019-02-18' AS Date), N'0123456789', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV008', N'Quận 7, Tp. Hồ Chí Minh', N'Nu', N'Lý Thị G', N'012344556789', CAST(N'1990-07-15' AS Date), CAST(N'2017-06-10' AS Date), N'0987654325', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV009', N'Quận 8, Tp. Hồ Chí Minh', N'Nam', N'Nguyễn Văn H', N'098765654321', CAST(N'1991-03-30' AS Date), CAST(N'2018-08-25' AS Date), N'0123456789', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV010', N'Quận 9, Tp. Hồ Chí Minh', N'Nu', N'Lê Thị I', N'012893456789', CAST(N'1992-08-05' AS Date), CAST(N'2020-01-12' AS Date), N'0987654326', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV011', N'Quận 10, Tp. Hồ Chí Minh', N'Nam', N'Hồ Văn K', N'095687654321', CAST(N'1989-06-20' AS Date), CAST(N'2016-12-08' AS Date), N'0123456789', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV012', N'Quận 7, Tp. Hồ Chí Minh', N'Nu', N'Trần Thị K', N'012123456786', CAST(N'1993-03-20' AS Date), CAST(N'2018-12-05' AS Date), N'0987654328', 0)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV013', N'Quận 6, Tp. Hồ Chí Minh', N'Nam', N'Võ Văn K', N'098741654329', CAST(N'1988-11-10' AS Date), CAST(N'2017-07-10' AS Date), N'0123456785', 0)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'NV014', N'Quận Tân Phú, TPHCM', N'Nam', N'Vũ Nguyễn Minh Đ', N'075203001069', CAST(N'2003-10-14' AS Date), CAST(N'2024-04-18' AS Date), N'0369160539', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD001', N'Quận Tân Phú, Tp. Hồ Chí Minh', N'Nam', N'Hồ Hoàng Ki', N'075203000052', CAST(N'1998-11-10' AS Date), CAST(N'1998-11-10' AS Date), N'0123456785', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD002', N'Quận 9, Tp. Hồ Chí Minh', N'Nam', N'Nguyễn Tiến L', N'075203000063', CAST(N'1990-01-15' AS Date), CAST(N'2018-08-20' AS Date), N'0123456789', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD003', N'Quận 2, Tp. Hồ Chí Minh', N'Nu', N'Cao Nguyễn Quỳnh N', N'075203000065', CAST(N'1989-03-15' AS Date), CAST(N'2018-10-15' AS Date), N'0123456791', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD004', N'Quận 3, Tp. Hồ Chí Minh', N'Nu', N'Lê Võ Quỳnh N', N'075203000066', CAST(N'1991-04-18' AS Date), CAST(N'2018-11-10' AS Date), N'0123456792', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD005', N'Quận 4, Tp. Hồ Chí Minh', N'Nam', N'Nguyễn Công P', N'075203000067', CAST(N'1987-05-22' AS Date), CAST(N'2018-12-15' AS Date), N'0123456793', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD006', N'Quận 5, Tp. Hồ Chí Minh', N'Nu', N'Trịnh Thị C', N'075203000068', CAST(N'1986-06-30' AS Date), CAST(N'2019-01-20' AS Date), N'0123456794', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD007', N'Quận 6, Tp. Hồ Chí Minh', N'Nam', N'Phạm An T', N'075203000069', CAST(N'1992-07-28' AS Date), CAST(N'2019-02-25' AS Date), N'0123456795', 0)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD008', N'Quận 7, Tp. Hồ Chí Minh', N'Nu', N'Nguyễn Lê Thảo T', N'075203000070', CAST(N'1984-08-19' AS Date), CAST(N'2019-03-30' AS Date), N'0123456796', 1)
INSERT [dbo].[CongNhanVien] ([maCongNhanVien], [diaChi], [gioiTinh], [hoTen], [maCanCuocCongDan], [ngaySinh], [ngayVaoLam], [soDienThoai], [trangThai]) VALUES (N'TLD009', N'Quận 8, Tp. Hồ Chí Minh', N'Nu', N'Nguyễn Ngọc T', N'075203000071', CAST(N'1983-09-15' AS Date), CAST(N'2019-04-10' AS Date), N'0123456797', 1)
GO
INSERT [dbo].[Dan] ([maSanPham], [can], [cauNgua], [day], [eoLung], [giaBan], [khoa], [loaiSanPham], [matDan], [matPhim], [moTa], [tenSanPham], [trangThai]) VALUES (N'SP001', N'Gỗ thao lao', N'Gỗ mật', N'Alice A107', N'Gỗ cẩm Ấn', 950000, N'Đài Loan', N'ACOUSTIC', N'Gỗ thông sitka', N'Gỗ mật', N'Bảo hành: 6 tháng', N'ĐÀN GUITAR CLASSIC VE-70-C', 1)
INSERT [dbo].[Dan] ([maSanPham], [can], [cauNgua], [day], [eoLung], [giaBan], [khoa], [loaiSanPham], [matDan], [matPhim], [moTa], [tenSanPham], [trangThai]) VALUES (N'SP002', N'Gỗ giá tỵ', N'Gỗ mun', N'Elixir', N'Gỗ cẩm Ấn', 1500000, N'Nhật Bản', N'CLASSIC', N'Thông Cedar', N'Gỗ mun', N'Bảo hành 5 tháng', N'Đàn Guitar Classic C-100', 1)
INSERT [dbo].[Dan] ([maSanPham], [can], [cauNgua], [day], [eoLung], [giaBan], [khoa], [loaiSanPham], [matDan], [matPhim], [moTa], [tenSanPham], [trangThai]) VALUES (N'SP004', N'Gỗ giá tỵ', N'Gỗ mật', N'Alice AW432', N'Gỗ hồng đào', 2000000, N'Nhật Bản', N'CLASSIC', N'Gỗ thông Sitka', N'Gỗ mật', N'Mo ta san pham 4', N'Đàn Guitar Classic C-100-J', 1)
INSERT [dbo].[Dan] ([maSanPham], [can], [cauNgua], [day], [eoLung], [giaBan], [khoa], [loaiSanPham], [matDan], [matPhim], [moTa], [tenSanPham], [trangThai]) VALUES (N'SP005', N'Gỗ thao lao', N'Gỗ mun', N'Elixir', N'Ván ép chất lượng cao', 1800000, N'Đài Loan', N'ACOUSTIC', N'Thông Cedar', N'Gỗ mun', N'Mo ta san pham 5', N'Đàn Guitar Acoustic D-200', 1)
GO
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'PhoPhong', 4160000, N'Đại học', N'NV001', N'PB002')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'TruongPhong', 3000000, N'Đại học', N'NV002', N'PB002')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'NhanVien', 4680000, N'Cao Đẳng', N'NV003', N'PB001')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'NhanVien', 3000000, N'Đại học', N'NV004', N'PB003')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'NhanVien', 4680000, N'Cao Đẳng', N'NV005', N'PB004')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'TruongPhong', 3000000, N'Đại học', N'NV006', N'PB005')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'PhoPhong', 4680000, N'Cao Đẳng', N'NV007', N'PB001')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'PhoPhong', 3000000, N'Đại học', N'NV008', N'PB002')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'TruongPhong', 3000000, N'Đại học', N'NV009', N'PB003')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'NhanVien', 3000000, N'Đại học', N'NV010', N'PB004')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'PhoPhong', 3000000, N'Đại học', N'NV011', N'PB005')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'NhanVien', 3000000, N'Cao Đẳng', N'NV012', N'PB004')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'PhoPhong', 3000000, N'Đại học', N'NV013', N'PB003')
INSERT [dbo].[NhanVien] ([chucVu], [luongCoBan], [trinhDoVanHoa], [maCongNhanVien], [maPhongBan]) VALUES (N'NhanVien', 4500000, N'Đại học', N'NV014', N'PB001')
GO
INSERT [dbo].[PhongBan] ([maPhongBan], [tenPhongBan]) VALUES (N'PB001', N'Marketing')
INSERT [dbo].[PhongBan] ([maPhongBan], [tenPhongBan]) VALUES (N'PB002', N'Nhân Sự')
INSERT [dbo].[PhongBan] ([maPhongBan], [tenPhongBan]) VALUES (N'PB003', N'Kinh Doanh')
INSERT [dbo].[PhongBan] ([maPhongBan], [tenPhongBan]) VALUES (N'PB004', N'Phát Triển Sản Phẩm')
INSERT [dbo].[PhongBan] ([maPhongBan], [tenPhongBan]) VALUES (N'PB005', N'Điều phối')
GO
INSERT [dbo].[TaiKhoan] ([taiKhoan], [matKhau], [maCongNhanVien]) VALUES (N'NV001', N'123', N'NV001')
INSERT [dbo].[TaiKhoan] ([taiKhoan], [matKhau], [maCongNhanVien]) VALUES (N'NV002', N'123', N'NV002')
INSERT [dbo].[TaiKhoan] ([taiKhoan], [matKhau], [maCongNhanVien]) VALUES (N'NV006', N'123', N'NV006')
INSERT [dbo].[TaiKhoan] ([taiKhoan], [matKhau], [maCongNhanVien]) VALUES (N'NV008', N'123', N'NV008')
INSERT [dbo].[TaiKhoan] ([taiKhoan], [matKhau], [maCongNhanVien]) VALUES (N'NV011', N'123', N'NV011')
GO
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac1', N'TLD001')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac1', N'TLD002')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac2', N'TLD003')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac3', N'TLD004')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac4', N'TLD005')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac5', N'TLD006')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac1', N'TLD007')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac2', N'TLD008')
INSERT [dbo].[ThoLamDan] ([tayNghe], [maCongNhanVien]) VALUES (N'Bac3', N'TLD009')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_r7qp2mrflkkl767n62cu4nult]    Script Date: 18/04/2024 8:57:22 CH ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_r7qp2mrflkkl767n62cu4nult] ON [dbo].[BangChamCongThoLamDan]
(
	[maCongDoan] ASC,
	[ngayPhanCong] ASC,
	[maCongNhanVien] ASC
)
WHERE ([maCongDoan] IS NOT NULL AND [ngayPhanCong] IS NOT NULL AND [maCongNhanVien] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_cxoqw4imrfmt6kroa5tsnxvjn]    Script Date: 18/04/2024 8:57:22 CH ******/
ALTER TABLE [dbo].[TaiKhoan] ADD  CONSTRAINT [UK_cxoqw4imrfmt6kroa5tsnxvjn] UNIQUE NONCLUSTERED 
(
	[maCongNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BangChamCongNhanVien]  WITH CHECK ADD  CONSTRAINT [FKfy79n3i4aryhvaho3obgdym0y] FOREIGN KEY([maCongNhanVien])
REFERENCES [dbo].[NhanVien] ([maCongNhanVien])
GO
ALTER TABLE [dbo].[BangChamCongNhanVien] CHECK CONSTRAINT [FKfy79n3i4aryhvaho3obgdym0y]
GO
ALTER TABLE [dbo].[BangChamCongNhanVien]  WITH CHECK ADD  CONSTRAINT [FKg2f5px64kon7cbcpp9jwxc3x1] FOREIGN KEY([maBangLuong])
REFERENCES [dbo].[BangLuongNhanVien] ([maBangLuong])
GO
ALTER TABLE [dbo].[BangChamCongNhanVien] CHECK CONSTRAINT [FKg2f5px64kon7cbcpp9jwxc3x1]
GO
ALTER TABLE [dbo].[BangChamCongThoLamDan]  WITH CHECK ADD  CONSTRAINT [FKhdjns33461laqp9tahdpcsy4] FOREIGN KEY([maBangLuong])
REFERENCES [dbo].[BangLuongThoLamDan] ([maBangLuong])
GO
ALTER TABLE [dbo].[BangChamCongThoLamDan] CHECK CONSTRAINT [FKhdjns33461laqp9tahdpcsy4]
GO
ALTER TABLE [dbo].[BangChamCongThoLamDan]  WITH CHECK ADD  CONSTRAINT [FKnhf3qpgmgvkl02fiydh8e8bt0] FOREIGN KEY([maCongDoan], [ngayPhanCong], [maCongNhanVien])
REFERENCES [dbo].[BangPhanCong] ([maCongDoan], [ngayPhanCong], [maCongNhanVien])
GO
ALTER TABLE [dbo].[BangChamCongThoLamDan] CHECK CONSTRAINT [FKnhf3qpgmgvkl02fiydh8e8bt0]
GO
ALTER TABLE [dbo].[BangLuongNhanVien]  WITH CHECK ADD  CONSTRAINT [FKi1mwejq6l7qgjfl4enfy923mb] FOREIGN KEY([maCongNhanVien])
REFERENCES [dbo].[NhanVien] ([maCongNhanVien])
GO
ALTER TABLE [dbo].[BangLuongNhanVien] CHECK CONSTRAINT [FKi1mwejq6l7qgjfl4enfy923mb]
GO
ALTER TABLE [dbo].[BangLuongThoLamDan]  WITH CHECK ADD  CONSTRAINT [FK4esyenoiclfbljbouiginsswn] FOREIGN KEY([maThoLamDan])
REFERENCES [dbo].[ThoLamDan] ([maCongNhanVien])
GO
ALTER TABLE [dbo].[BangLuongThoLamDan] CHECK CONSTRAINT [FK4esyenoiclfbljbouiginsswn]
GO
ALTER TABLE [dbo].[BangPhanCong]  WITH CHECK ADD  CONSTRAINT [FK1c0bqi7xdg7rbtdm7g2g1p0lw] FOREIGN KEY([maCongDoan])
REFERENCES [dbo].[CongDoan] ([maCongDoan])
GO
ALTER TABLE [dbo].[BangPhanCong] CHECK CONSTRAINT [FK1c0bqi7xdg7rbtdm7g2g1p0lw]
GO
ALTER TABLE [dbo].[BangPhanCong]  WITH CHECK ADD  CONSTRAINT [FK4xgkd3l6xxrb0h63bhdorh145] FOREIGN KEY([maCongNhanVien])
REFERENCES [dbo].[ThoLamDan] ([maCongNhanVien])
GO
ALTER TABLE [dbo].[BangPhanCong] CHECK CONSTRAINT [FK4xgkd3l6xxrb0h63bhdorh145]
GO
ALTER TABLE [dbo].[CongDoan]  WITH CHECK ADD  CONSTRAINT [FK4ohp0jujok479r783trutknuu] FOREIGN KEY([maSanPham])
REFERENCES [dbo].[Dan] ([maSanPham])
GO
ALTER TABLE [dbo].[CongDoan] CHECK CONSTRAINT [FK4ohp0jujok479r783trutknuu]
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD  CONSTRAINT [FK5aualjsf9dkmvxtu7hurhjt67] FOREIGN KEY([maCongNhanVien])
REFERENCES [dbo].[CongNhanVien] ([maCongNhanVien])
GO
ALTER TABLE [dbo].[NhanVien] CHECK CONSTRAINT [FK5aualjsf9dkmvxtu7hurhjt67]
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD  CONSTRAINT [FKli52j4ms67nk3b3h336521jqq] FOREIGN KEY([maPhongBan])
REFERENCES [dbo].[PhongBan] ([maPhongBan])
GO
ALTER TABLE [dbo].[NhanVien] CHECK CONSTRAINT [FKli52j4ms67nk3b3h336521jqq]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FKedjualb5hvlyq8o3k4lrw8her] FOREIGN KEY([maCongNhanVien])
REFERENCES [dbo].[NhanVien] ([maCongNhanVien])
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FKedjualb5hvlyq8o3k4lrw8her]
GO
ALTER TABLE [dbo].[ThoLamDan]  WITH CHECK ADD  CONSTRAINT [FKp9aosyx2b00qkyt7m1p01oc1w] FOREIGN KEY([maCongNhanVien])
REFERENCES [dbo].[CongNhanVien] ([maCongNhanVien])
GO
ALTER TABLE [dbo].[ThoLamDan] CHECK CONSTRAINT [FKp9aosyx2b00qkyt7m1p01oc1w]
GO
ALTER TABLE [dbo].[CongNhanVien]  WITH CHECK ADD CHECK  (([gioiTinh]='Nu' OR [gioiTinh]='Nam'))
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD CHECK  (([chucVu]='TruongPhong' OR [chucVu]='PhoPhong' OR [chucVu]='NhanVien'))
GO
ALTER TABLE [dbo].[ThoLamDan]  WITH CHECK ADD CHECK  (([tayNghe]='Bac5' OR [tayNghe]='Bac4' OR [tayNghe]='Bac3' OR [tayNghe]='Bac2' OR [tayNghe]='Bac1'))
GO
USE [master]
GO
ALTER DATABASE [luongsp] SET  READ_WRITE 
GO
